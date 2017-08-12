package spark.jobserver.client;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * A client implements all the Rest APIs described by the
 *  Job Server (https://github.com/ooyala/spark-jobserver).
 */
public interface IJobServerClient {
	/**
	 * Lists all the information of jars for potential jobs to be running
	 * in the  Cluster behind the  Job Server.
	 * 
	 * <p>
	 * This method implements the Rest API <code>'GET /jars' </code>of the 
	 *  Job Server.
	 * 
	 * @return a list containing information of  Job jars
	 * @throws JobServerClientException error occurs when trying to get 
	 *         information of spark job jars
	 */
	List<JarInfo> getJars() throws JobServerClientException;
	
	/**
	 * Uploads a jar containing spark job to the  Job Server under
	 * the given application name.
	 * 
	 * <p>
	 * This method implements the Rest API <code>'POST /jars/&lt;appName&gt;' </code>
	 * of the  Job Server.
	 * 
	 * @param jarData the instance of <code>InputStream</code> contains the
	 *     contents of the target jar file to be uploaded
	 * @param appName the application name under which the related  Job
	 *     is about to run, meanwhile the application name also be the alias
	 *     name of the uploaded jar file.
	 * @return true if the operation of uploading is successful, false otherwise
	 * @throws JobServerClientException if the given parameter jarData or
	 *     appName is null, or error occurs when uploading the related spark job 
	 *     jar
	 */
	boolean uploadJobJar(InputStream jarData, String appName) 
        throws JobServerClientException;
	
	/**
	 * Uploads a jar containing spark job to the  Job Server under
	 * the given application name.
	 * 
	 * <p>
	 * This method implements the Rest API <code>'POST /jars/&lt;appName&gt;' </code>
	 * of the  Job Server.
	 * 
	 * @param jarFile the jar file
	 * @param appName the application name under which the related  Job
	 *     is about to run, meanwhile the application name also be the alias
	 *     name of the uploaded jar file.
	 * @return true if the operation of uploading is successful, false otherwise
	 * @throws JobServerClientException if the given parameter jarData or
	 *     appName is null, or error occurs when uploading the related spark job 
	 *     jar
	 */
	boolean uploadJobJar(File jarFile, String appName)
	    throws JobServerClientException;
	
	/**
	 * Lists all the contexts available in the  Job Server.
	 * 
	 * <p>
	 * This method implements the Rest API <code>'GET /contexts '</code>
	 * of the  Job Server.
	 * 
	 * @return a list containing names of current contexts
	 * @throws JobServerClientException error occurs when trying to get 
	 *         information of contexts
	 */
	List<String> getContexts() throws JobServerClientException;
	
	/**
	 * Creates a new context in the  Job Server with the given context name.
	 * 
	 * <p>
	 * This method implements the Rest API <code>'POST /contexts/&lt;name&gt;' </code>
	 * of the  Job Server.
	 * 
	 * @param contextName the name of the new context to be created, it should be not null
	 *        and should begin with letter.
	 * @param params a map containing the key-value pairs appended to appoint the context 
	 *        settings if there is a need to configure the new created context, or null indicates
	 *        the new context with the default configuration
	 * @return true if the operation of creating is successful, false it failed to create
	 *        the context because a context with the same name already exists
	 * @throws JobServerClientException when the given contextName is null or empty string,
	 *        or I/O error occurs while trying to create context in spark job server.
	 */
	boolean createContext(String contextName, Map<String, String> params) throws JobServerClientException;
	
	/**
	 * Delete a context with the given name in the  Job Server.
	 * All the jobs running in it will be stopped consequently.
	 * 
	 * <p>
	 * This method implements the Rest API <code>'DELETE /contexts/&lt;name&gt;' </code>
	 * of the  Job Server.
	 * 
	 * @param contextName the name of the target context to be deleted, it should be not null
	 *        and should begin with letter.
	 * @return true if the operation of the deleting is successful, false otherwise
	 * @throws JobServerClientException when the given contextName is null or empty string,
	 *        or I/O error occurs while trying to delete context in spark job server.
	 */
	boolean deleteContext(String contextName) throws JobServerClientException;
	
	/**
	 * Lists the last N jobs in the  Job Server.
	 * 
	 * <p>
	 * This method implements the Rest API <code>'GET /jobs' </code> of the 
	 * Job Server.
	 * 
	 * @return a list containing information of the jobs
	 * @throws JobServerClientException error occurs when trying to get 
	 *         information of jobs
	 */
	List<JobInfo> getJobs() throws JobServerClientException;
	
	/**
	 * Start a new job with the given parameters.
	 * 
	 * <p>
	 * This method implements the Rest API <code>'POST /jobs' </code> of the 
	 * Job Server.
	 * 
	 * @param data contains the the data processed by the target job.
	 * 		 <p>
	 * 	     If it is null, it means the target spark job doesn't needs any data set
	 *       in the job configuration.
	 * 		 <p>
	 * 	     If it is not null, the format should be like a key-value pair, such as 
	 * 	     <code>dataKey=dataValue</code>, what the dataKey is determined by the 
	 * 		 one used in the target spark job main class which is assigned by 
	 *       IJobServerClientConstants.PARAM_CLASS_PATH.
	 * @param params a non-null map containing parameters to start the job.
	 *       the key should be the following ones:
	 *       i. <code>IJobServerClientConstants.PARAM_APP_NAME</code>, necessary 
	 *       one and should be one of the existing name in the calling of <code>GET /jars</code>.
	 *       That means the appName is the alias name of the uploaded spark job jars.
	 *
	 *       ii.<code>IJobServerClientConstants.PARAM_CLASS_PATH</code>, necessary one
	 *
	 *       iii.<code>IJobServerClientConstants.PARAM_CONTEXT</code>, optional one
	 *
	 *       iv.<code>IJobServerClientConstants.PARAM_SYNC</code>, optional one
	 *
	 * @return the corresponding job status or job result
	 * @throws JobServerClientException the given parameters exist null or empty value,
	 *        or I/O error occurs when trying to start the new job
	 */
	JobInfo startJob(String data, Map<String, String> params) throws JobServerClientException;

	/**
	 * Start a new job with the given parameters.
	 *
	 * <p>
	 * This method implements the Rest API <code>'POST /jobs' </code> of the 
	 * Job Server.
	 *
	 * @param dataFile contains the the data processed by the target job.
	 * 		 <p>
	 * 	     If it is null, it means the target spark job doesn't needs any data set
	 *       in the job configuration.
	 * 		 <p>
	 * 	     If it is not null, the format should be Typesafe Config Style, such as
	 * 	     json, properties file etc. See <a href="http://github.com/typesafehub/config">http://github.com/typesafehub/config</a>
	 * 	     what the keys in the file are determined by the
	 * 		 one used in the target spark job main class which is assigned by
	 *       IJobServerClientConstants.PARAM_CLASS_PATH.
	 * @param params a non-null map containing parameters to start the job.
	 *       the key should be the following ones:
	 *       i. <code>IJobServerClientConstants.PARAM_APP_NAME</code>, necessary
	 *       one and should be one of the existing name in the calling of <code>GET /jars</code>.
	 *       That means the appName is the alias name of the uploaded spark job jars.
	 *
	 *       ii.<code>IJobServerClientConstants.PARAM_CLASS_PATH</code>, necessary one
	 *
	 *       iii.<code>IJobServerClientConstants.PARAM_CONTEXT</code>, optional one
	 *
	 *       iv.<code>IJobServerClientConstants.PARAM_SYNC</code>, optional one
	 *
	 * @return the corresponding job status or job result
	 * @throws JobServerClientException the given parameters exist null or empty value,
	 *        or I/O error occurs when trying to start the new job
	 */
	JobInfo startJob(File dataFile, Map<String, String> params) throws JobServerClientException;

	/**
	 * Start a new job with the given parameters.
	 *
	 * <p>
	 * This method implements the Rest API <code>'POST /jobs' </code> of the 
	 * Job Server.
	 *
	 * @param dataFileStream contains the the data processed by the target job.
	 * 		 <p>
	 * 	     If it is null, it means the target spark job doesn't needs any data set
	 *       in the job configuration.
	 * 		 <p>
	 * 	     If it is not null, the format should be Typesafe Config Style, such as
	 * 	     json, properties file etc. See <a href="http://github.com/typesafehub/config">http://github.com/typesafehub/config</a>
	 * 	     what the keys in the file are determined by the
	 * 		 one used in the target spark job main class which is assigned by
	 *       IJobServerClientConstants.PARAM_CLASS_PATH.
	 * @param params a non-null map containing parameters to start the job.
	 *       the key should be the following ones:
	 *       i. <code>IJobServerClientConstants.PARAM_APP_NAME</code>, necessary
	 *       one and should be one of the existing name in the calling of <code>GET /jars</code>.
	 *       That means the appName is the alias name of the uploaded spark job jars.
	 *
	 *       ii.<code>IJobServerClientConstants.PARAM_CLASS_PATH</code>, necessary one
	 *
	 *       iii.<code>IJobServerClientConstants.PARAM_CONTEXT</code>, optional one
	 *
	 *       iv.<code>IJobServerClientConstants.PARAM_SYNC</code>, optional one
	 *
	 * @return the corresponding job status or job result
	 * @throws JobServerClientException the given parameters exist null or empty value,
	 *        or I/O error occurs when trying to start the new job
	 */
	JobInfo startJob(InputStream dataFileStream, Map<String, String> params) throws JobServerClientException;

	/**
	 * Gets the result or status of a specific job in the  Job Server.
	 * 
	 * <p>
	 * This method implements the Rest API <code>'GET /jobs/&lt;jobId&gt;' </code>
	 * of the  Job Server.
	 * 
	 * @param jobId the id of the target job
	 * @return the corresponding <code>JobResult</code> instance if the job
	 * with the given jobId exists, or null if there is no corresponding job or
	 * the target job has no result.
	 * @throws JobServerClientException error occurs when trying to get 
	 *         information of the target job
	 */
	JobInfo getJobResult(String jobId) throws JobServerClientException;
	
	/**
	 * Gets the job configuration of a specific job.
	 * 
	 * <p>
	 * This method implements the Rest API <code>'GET /jobs/&lt;jobId&gt;/config' </code>
	 * of the  Job Server.
	 * 
	 * @param jobId the id of the target job
	 * @return the corresponding <code>JobConfig</code> instance if the job
	 * with the given jobId exists, or null if there is no corresponding job in 
	 * the spark job server.
	 * @throws JobServerClientException error occurs when trying to get 
	 *         information of the target job configuration
	 */
	JobConfig getConfig(String jobId) throws JobServerClientException;
}
