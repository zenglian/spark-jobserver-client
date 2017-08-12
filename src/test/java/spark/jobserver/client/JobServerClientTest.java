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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spark.jobserver.client.IJobServerClient;
import spark.jobserver.client.IJobServerClientConstants;
import spark.jobserver.client.JobInfo;
import spark.jobserver.client.JobServerClientFactory;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

/**
 * a test class for JobServerClientImpl
 */
public class JobServerClientTest {
	private static final String defaultJobHost = "192.168.1.103";
	private static final String defaultJobPort = "8090";
	private static String endpoint = String.format("http://%s:%s/", defaultJobHost, defaultJobPort);
	private IJobServerClient client;
	private static final long POOLING_TIME_SEC = 1;

	@Before
	public void setUp() throws Exception {
		client = JobServerClientFactory.getInstance().createJobServerClient(endpoint);
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testJobs() throws Exception {
		List<JobInfo> jobs = client.getJobs();
		
		for(JobInfo job: jobs){
			if(job.isFinished()){
				JobInfo r = client.getJobResult(job.getJobId());
				System.out.println(r);
			}
			else	
				System.out.println(job);
			
			JobConfig config = client.getConfig(job.getJobId());
			System.out.println(config);
		}
	}
	
	@Test
	public void testJars() throws Exception {
		for(JarInfo jar: client.getJars()){
			System.out.println(jar);
		}
	}
	
	@Test
	public void testContexts() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put(IJobServerClientConstants.PARAM_NUM_CPU_CORES, "2");
		client.createContext("context1", params);
		
		System.out.println(Pojo.gson.toJson(client.getContexts()));
	}
	
	
	/**
	 * test runJob with File resource Warning: This test require deleting jar
	 * after test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRunJobWithFile() throws Exception {
		InputStream jarFileStream = ClassLoader.getSystemResourceAsStream("./job-server-tests.jar");
		File inputData = new File(ClassLoader.getSystemResource("input.json").toURI());

		String appName = "runjob-with-file-test";
		boolean isUploaded = client.uploadJobJar(jarFileStream, appName);

		assertThat(isUploaded, is(true));

		Map<String, String> params = new HashMap<String, String>();
		params.put(IJobServerClientConstants.PARAM_APP_NAME, appName);
		params.put(IJobServerClientConstants.PARAM_CLASS_PATH, "spark.jobserver.WordCountExample");
		params.put(IJobServerClientConstants.PARAM_SYNC, "true");

		JobInfo result = client.startJob(inputData, params);
		while (result.isRunning()) {
			TimeUnit.SECONDS.sleep(POOLING_TIME_SEC);
			result = client.getJobResult(result.getJobId());
		}

		assertThat(result.getResultAsString(), is("{\"fdsafd\":1,\"a\":4,\"b\":1,\"dfsf\":1,\"c\":1}"));
	}

	/**
	 * Warning: This test require deleting jar after test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUploadJar() throws Exception {
		InputStream jarFileStream = ClassLoader.getSystemResourceAsStream("./job-server-tests.jar");

		String appName = "upload-jar-test";
		boolean isUploaded = client.uploadJobJar(jarFileStream, appName);

		assertThat(isUploaded, is(true));

		Map<String, String> params = new HashMap<String, String>();
		params.put(IJobServerClientConstants.PARAM_APP_NAME, appName);
		params.put(IJobServerClientConstants.PARAM_CLASS_PATH, "spark.jobserver.WordCountExample");
		params.put(IJobServerClientConstants.PARAM_SYNC, "true");
		
		JobInfo result = client.startJob("input.string= fdsafd dfsf a b c a a a ", params);
		assertThat(result.getResultAsString(), is("{\"fdsafd\":1,\"a\":4,\"b\":1,\"dfsf\":1,\"c\":1}"));
	}
}