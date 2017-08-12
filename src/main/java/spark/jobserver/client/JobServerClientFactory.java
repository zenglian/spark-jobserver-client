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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
 * The factory is responsible for creating instance of <code>IJobServerClient</code>
 * to communicate with the  Job Server with the arranged rest apis.
 */
public final class JobServerClientFactory {
	private static final JobServerClientFactory INSTANCE = new JobServerClientFactory();
	
	private static Logger logger = Logger.getLogger(JobServerClientFactory.class);
	
	private static Map<String, IJobServerClient> jobServerClientCache 
	    = new ConcurrentHashMap<String, IJobServerClient>();
	
	/**
	 * The default constructor of <code>JobServerClientFactory</code>. 
	 */
	private JobServerClientFactory() {
	}
	
	/**
	 * Gets the unique instance of <code>JobServerClientFactory</code>.
	 * @return the instance of <code>JobServerClientFactory</code>
	 */
	public static JobServerClientFactory getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Creates an instance of <code>IJobServerClient</code> with the given url.
	 * 
	 * @param url the url of the target  Job Server
	 * @return the corresponding <code>IJobServerClient</code> instance
	 * @throws JobServerClientException error occurs when trying to create the 
	 *     target spark job server client
	 */
	public IJobServerClient createJobServerClient(String url) 
		throws JobServerClientException {
		if (!isValidUrl(url)) {
			throw new JobServerClientException("Invalid url can't be used to create a spark job server client.");
		}
		String sparkJobServerUrl = url.trim();
		IJobServerClient sparkJobServerClient = jobServerClientCache.get(sparkJobServerUrl);
		if (null == sparkJobServerClient) {
			sparkJobServerClient = new JobServerClient(url);
			jobServerClientCache.put(url, sparkJobServerClient);
		}
		return sparkJobServerClient;
	}
	
	/**
	 * Checks the given url is valid or not.
	 * 
	 * @param url the url to be checked
	 * @return true if it is valid, false otherwise
	 */
	private boolean isValidUrl(String url) {
		if (url == null || url.trim().length() <= 0) {
			logger.error("The given url is null or empty.");
			return false;
		}
		try {
			new URL(url);
		} catch (MalformedURLException me) {
			StringBuffer buff = new StringBuffer("The given url ");
			buff.append(url).append(" is invalid.");
			logger.error(buff.toString(), me);
		}
		return true;
	}
}
