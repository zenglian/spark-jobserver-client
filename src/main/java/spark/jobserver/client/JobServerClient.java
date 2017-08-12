/*
 * Copyright 2014-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package spark.jobserver.client;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static spark.jobserver.client.Pojo.gson;

import com.google.gson.*;
import com.google.gson.reflect.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

/**
 * The default client implementation of <code>IJobServerClient</code>.
 * With the specific rest api, it can provide abilities to submit and manage 
 * Apache  jobs, jars, and job contexts in the  Job Server.
 *
 */
public class JobServerClient implements IJobServerClient {
	private static Logger logger = Logger.getLogger(JobServerClient.class);
	private static final int BUFFER_SIZE = 512 * 1024;
	public final static transient JsonParser parser = new JsonParser();
	private String jobServerUrl;

	/**
	 * Constructs an instance of <code>JobServerClientImpl</code>
	 * with the given spark job server url.
	 * 
	 * @param jobServerUrl a url pointing to a existing spark job server
	 */
	JobServerClient(String jobServerUrl) {
		if (!jobServerUrl.endsWith("/")) {
			jobServerUrl = jobServerUrl + "/";
		}
		this.jobServerUrl = jobServerUrl;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<JarInfo> getJars() throws JobServerClientException {
		List<JarInfo> sparkJobJarInfos = new ArrayList<JarInfo>();
		final CloseableHttpClient httpClient = buildClient();
		try {
			HttpGet getMethod = new HttpGet(jobServerUrl + "jars");
			HttpResponse response = httpClient.execute(getMethod);
			int statusCode = response.getStatusLine().getStatusCode();
			String resContent = getResponseContent(response.getEntity());
			if (statusCode == HttpStatus.SC_OK) {
				TreeMap<String, Date> jars = gson.fromJson(resContent, new TypeToken<TreeMap<String, Date>>(){}.getType());
				for(String name: jars.keySet()){
					sparkJobJarInfos.add(new JarInfo(name, jars.get(name)));
				}
			} else {
				logError(statusCode, resContent, true);
			}
		} catch (Exception e) {
			processException("Error occurs when trying to get information of jars:", e);
		} finally {
			close(httpClient);
		}
		return sparkJobJarInfos;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean uploadJobJar(InputStream jarData, String appName)
	    throws JobServerClientException {
		if (jarData == null || appName == null || appName.trim().length() == 0) {
			throw new JobServerClientException("Invalid parameters.");
		}
		HttpPost postMethod = new HttpPost(jobServerUrl + "jars/" + appName);

		final CloseableHttpClient httpClient = buildClient();
		try {
			ByteArrayEntity entity = new ByteArrayEntity(IOUtils.toByteArray(jarData));
			postMethod.setEntity(entity);
			entity.setContentType("application/java-archive");
			HttpResponse response = httpClient.execute(postMethod);
			int statusCode = response.getStatusLine().getStatusCode();
			getResponseContent(response.getEntity());
			if (statusCode == HttpStatus.SC_OK) {
				return true;
			}
		} catch (Exception e) {
			logger.error("Error occurs when uploading spark job jars:", e);
		} finally {
			close(httpClient);
			closeStream(jarData);
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean uploadJobJar(File jarFile, String appName)
		    throws JobServerClientException {
		if (jarFile == null || !jarFile.getName().endsWith(".jar") 
			|| appName == null || appName.trim().length() == 0) {
			throw new JobServerClientException("Invalid parameters.");
		}
		InputStream jarIn = null;
		try {
			jarIn = new FileInputStream(jarFile);
		} catch (FileNotFoundException fnfe) {
			String errorMsg = "Error occurs when getting stream of the given jar file";
			logger.error(errorMsg, fnfe);
			throw new JobServerClientException(errorMsg, fnfe);
		}
		return uploadJobJar(jarIn, appName);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getContexts() throws JobServerClientException {
		List<String> contexts = new ArrayList<String>();
		final CloseableHttpClient httpClient = buildClient();
		try {
			HttpGet getMethod = new HttpGet(jobServerUrl + "contexts");
			HttpResponse response = httpClient.execute(getMethod);
			int statusCode = response.getStatusLine().getStatusCode();
			String resContent = getResponseContent(response.getEntity());
			if (statusCode == HttpStatus.SC_OK) {
				contexts = gson.fromJson(resContent, new TypeToken<ArrayList<String>>(){}.getType());
			} else {
				logError(statusCode, resContent, true);
			}
		} catch (Exception e) {
			processException("Error occurs when trying to get information of contexts:", e);
		} finally {
			close(httpClient);
		}
		return contexts;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean createContext(String contextName, Map<String, String> params) 
		throws JobServerClientException {
		final CloseableHttpClient httpClient = buildClient();
		try {
			//TODO add a check for the validation of contextName naming
			if (!isNotEmpty(contextName)) {
				throw new JobServerClientException("The given contextName is null or empty.");
			}
			StringBuffer postUrlBuff = new StringBuffer(jobServerUrl);
			postUrlBuff.append("contexts/").append(contextName);
			if (params != null && !params.isEmpty()) {
				postUrlBuff.append('?');
				int num = params.size();
				for (String key : params.keySet()) {
					postUrlBuff.append(key).append('=').append(params.get(key));
					num--;
					if (num > 0) {
						postUrlBuff.append('&');
					}
				}
				
			}
			HttpPost postMethod = new HttpPost(postUrlBuff.toString());
			HttpResponse response = httpClient.execute(postMethod);
			int statusCode = response.getStatusLine().getStatusCode();
			String resContent = getResponseContent(response.getEntity());
			if (statusCode == HttpStatus.SC_OK) {
				return true;
			} else {
				logError(statusCode, resContent, false);
			}
		} catch (Exception e) {
			processException("Error occurs when trying to create a context:", e);
		} finally {
			close(httpClient);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deleteContext(String contextName) 
		throws JobServerClientException {
		final CloseableHttpClient httpClient = buildClient();
		try {
			//TODO add a check for the validation of contextName naming
			if (!isNotEmpty(contextName)) {
				throw new JobServerClientException("The given contextName is null or empty.");
			}
			StringBuffer postUrlBuff = new StringBuffer(jobServerUrl);
			postUrlBuff.append("contexts/").append(contextName);
			
			HttpDelete deleteMethod = new HttpDelete(postUrlBuff.toString());
			HttpResponse response = httpClient.execute(deleteMethod);
			int statusCode = response.getStatusLine().getStatusCode();
			String resContent = getResponseContent(response.getEntity());
			if (statusCode == HttpStatus.SC_OK) {
				return true;
			} else {
				logError(statusCode, resContent, false);
			}
		} catch (Exception e) {
			processException("Error occurs when trying to delete the target context:", e);
		} finally {
			close(httpClient);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<JobInfo> getJobs() throws JobServerClientException {
		List<JobInfo> sparkJobInfos = new ArrayList<JobInfo>();
		final CloseableHttpClient httpClient = buildClient();
		try {
			HttpGet getMethod = new HttpGet(jobServerUrl + "jobs");
			HttpResponse response = httpClient.execute(getMethod);
			int statusCode = response.getStatusLine().getStatusCode();
			String resContent = getResponseContent(response.getEntity());
			if (statusCode == HttpStatus.SC_OK) {
				sparkJobInfos = gson.fromJson(resContent, new TypeToken<ArrayList<JobInfo>>(){}.getType());
			} else {
				logError(statusCode, resContent, true);
			}
		} catch (Exception e) {
			processException("Error occurs when trying to get information of jobs:", e);
		} finally {
			close(httpClient);
		}
		return sparkJobInfos;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public JobInfo startJob(String data, Map<String, String> params) throws JobServerClientException {
		final CloseableHttpClient httpClient = buildClient();
		try {
			if (params == null || params.isEmpty()) {
				throw new JobServerClientException("The given params is null or empty.");
			}
			if (params.containsKey(IJobServerClientConstants.PARAM_APP_NAME) &&
			    params.containsKey(IJobServerClientConstants.PARAM_CLASS_PATH)) {
				StringBuffer postUrlBuff = new StringBuffer(jobServerUrl);
				postUrlBuff.append("jobs?");
				int num = params.size();
				for (String key : params.keySet()) {
					postUrlBuff.append(key).append('=').append(params.get(key));
					num--;
					if (num > 0) {
						postUrlBuff.append('&');
					}
				}
				HttpPost postMethod = new HttpPost(postUrlBuff.toString());
				if (data != null) {
					StringEntity strEntity = new StringEntity(data);
					strEntity.setContentEncoding("UTF-8");
					strEntity.setContentType("text/plain");
					postMethod.setEntity(strEntity);
				}
				
				HttpResponse response = httpClient.execute(postMethod);
				String resContent = getResponseContent(response.getEntity());
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_ACCEPTED) {
					return parseResult(resContent);
				} else {
					logError(statusCode, resContent, true);
				}
			} else {
				throw new JobServerClientException("The given params should contains appName and classPath");
			}
		} catch (Exception e) {
			processException("Error occurs when trying to start a new job:", e);
		} finally {
			close(httpClient);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public JobInfo startJob(InputStream dataFileStream, Map<String, String> params) throws JobServerClientException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(dataFileStream));
			String data = br.lines().collect(Collectors.joining(System.lineSeparator()));
			return startJob(data, params);
		} catch (Exception e) {
			processException("Error occurs when reading inputstream:", e);
		} finally {
			closeStream(br);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public JobInfo startJob(File dataFile, Map<String, String> params) throws JobServerClientException {
		InputStream dataFileStream = null;
		try {
			dataFileStream = new FileInputStream(dataFile);
			return startJob(dataFileStream, params);
		} catch (Exception e) {
			processException("Error occurs when reading file:", e);
		} finally {
			closeStream(dataFileStream);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public JobInfo getJobResult(String jobId) throws JobServerClientException {
		final CloseableHttpClient httpClient = buildClient();
		try {
			if (!isNotEmpty(jobId)) {
				throw new JobServerClientException("The given jobId is null or empty.");
			}
			HttpGet getMethod = new HttpGet(jobServerUrl + "jobs/" + jobId);
			HttpResponse response = httpClient.execute(getMethod);
			String resContent = getResponseContent(response.getEntity());
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				final JobInfo jobResult = parseResult(resContent);
				jobResult.setJobId(jobId);
				return jobResult;
			} else if (statusCode == HttpStatus.SC_NOT_FOUND) {
				return new JobInfo(resContent, jobId);
			} else {
				logError(statusCode, resContent, true);
			}
		} catch (Exception e) {
			processException("Error occurs when trying to get information of the target job:", e);
		} finally {
			close(httpClient);
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public JobConfig getConfig(String jobId) throws JobServerClientException {
		final CloseableHttpClient httpClient = buildClient();
		try {
			if (!isNotEmpty(jobId)) {
				throw new JobServerClientException("The given jobId is null or empty.");
			}
			HttpGet getMethod = new HttpGet(jobServerUrl + "jobs/" + jobId + "/config");
			HttpResponse response = httpClient.execute(getMethod);
			String resContent = getResponseContent(response.getEntity());
			JobConfig jobConfg = new JobConfig();
			HashMap<String,Object> configs = gson.fromJson(resContent, new TypeToken<HashMap<String,Object>>(){}.getType());
			jobConfg.setConfigs(configs);
			return jobConfg;
		} catch (Exception e) {
			processException("Error occurs when trying to get information of the target job config:", e);
		} finally {
			close(httpClient);
		}
		return null;
	}

	/**
	 * Gets the contents of the http response from the given <code>HttpEntity</code>
	 * instance.
	 * 
	 * @param entity the <code>HttpEntity</code> instance holding the http response content
	 * @return the corresponding response content
	 */
	protected String getResponseContent(HttpEntity entity) {
		byte[] buff = new byte[BUFFER_SIZE];
		StringBuffer contents = new StringBuffer();
		InputStream in = null;
		try {
			in = entity.getContent();
			BufferedInputStream bis = new BufferedInputStream(in);
			int readBytes = 0;
			while ((readBytes = bis.read(buff)) != -1) {
				contents.append(new String(buff, 0, readBytes));
			}
		} catch (Exception e) {
			logger.error("Error occurs when trying to reading response", e);
		} finally {
			closeStream(in);
		}
		return contents.toString().trim();
	}
	
	/**
	 * Closes the given stream.
	 * 
	 * @param stream the input/output stream to be closed
	 */
	protected void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException ioe) {
				logger.error("Error occurs when trying to close the stream:", ioe);
			}
		} else {
			logger.error("The given stream is null");
		}
	}
	
	/**
	 * Handles the given exception with specific error message, and
	 * generates a corresponding <code>JobServerClientException</code>.
	 * 
	 * @param errorMsg the corresponding error message
	 * @param e the exception to be handled
	 * @throws JobServerClientException the corresponding transformed 
	 *        <code>JobServerClientException</code> instance
	 */
	protected void processException(String errorMsg, Exception e) throws JobServerClientException {
		if (e instanceof JobServerClientException) {
			throw (JobServerClientException)e;
		}
		logger.error(errorMsg, e);
		throw new JobServerClientException(errorMsg, e);
	}
	
	/**
	 * Judges the given string value is not empty or not.
	 * 
	 * @param value the string value to be checked
	 * @return true indicates it is not empty, false otherwise
	 */
	protected boolean isNotEmpty(String value) {
		return value != null && !value.isEmpty();
	}
	
	/**
	 * Logs the response information when the status is not 200 OK,
	 * and throws an instance of <code>JobServerClientException<code>.
	 * 
	 * @param errorStatusCode error status code
	 * @param msg the message to indicates the status, it can be null
	 * @param throwable true indicates throws an instance of <code>JobServerClientException</code>
	 *       with corresponding error message, false means only log the error message.
	 * @throws JobServerClientException containing the corresponding error message 
	 */
	private void logError(int errorStatusCode, String msg, boolean throwable) throws JobServerClientException {
		StringBuffer msgBuff = new StringBuffer(" Job Server ");
		msgBuff.append(jobServerUrl).append(" response ").append(errorStatusCode);
		if (null != msg) {
			msgBuff.append(" ").append(msg);
		}
		String errorMsg = msgBuff.toString();
		logger.error(errorMsg);
		if (throwable) {
			throw new JobServerClientException(errorMsg);
		}
	}
	

	/**
	 * Generates an instance of <code>JobResult</code> according to the given contents.
	 * 
	 * @param resContent the content of a http response
	 * @return the corresponding <code>JobResult</code> instance
	 * @throws Exception error occurs when parsing the http response content
	 */
	private JobInfo parseResult(String resContent) throws Exception {
		JobInfo jobResult = gson.fromJson(resContent, JobInfo.class);
		return jobResult;
	}
	
	private CloseableHttpClient buildClient() {
		return HttpClientBuilder.create().build();
	}

	private void close(final CloseableHttpClient client) {
		try {
			client.close();
		} catch (final IOException e) {
			logger.error("could not close client" , e);
		}
	}
}
