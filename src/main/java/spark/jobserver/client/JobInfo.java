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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Presents the information of spark job result, when calling 
 * <code>GET /jobs/&lt;jobId&gt;</code> to a spark job server.
 * 
 */
@Getter @Setter
public class JobInfo extends Pojo {
	private String jobId;
	private JobStatus status;
	private String context;
	private String classPath;
	private String duration;
	private Date startTime;
	
	@SerializedName("result")
	private JsonObject result; 
	private String contents; 
	private Map<String, Object> extendAttributes = new HashMap<String, Object>();

	public JobInfo(String contents, String jobId) {
		this.contents = contents;
		setJobId(jobId);
	}

	public JobInfo(String contents) {
		this(contents, null);
	}	

	public void putExtendAttribute(String key, Object value) {
		this.extendAttributes.put(key, value);
	}	
	

	/**
	 * Judges current <code>JobResult</code> instance represents the 
	 * status information of a asynchronous running spark job or not.
	 * 
	 * @return true indicates it contains asynchronous running status of a
	 *         spark job, false otherwise
	 */
	public boolean isRunning() {
		return getStatus() == JobStatus.STARTED || getStatus() == JobStatus.RUNNING;		
	}

	public boolean isFinished(){
		return getStatus() == JobStatus.FINISHED || getStatus() == JobStatus.OK;		
	}
	
	/**
	 * If status is error.
	 * @return
	 */
	public boolean isError(){
		return status == JobStatus.ERROR;
	}		
	
	/**
	 * Judges the queried target job doesn't exist or not.
	 * 
	 * @return true indicates the related job doesn't exist, false otherwise
	 */
	public boolean jobNotExists() {
		return getStatus() == JobStatus.ERROR && getResult().toString().contains("No such job ID");
	}
	
	/**
	 * Judges current <code>JobResult</code> instance contains 
	 * custom-defined extend attributes of result or not
	 * 
	 * @return true indicates it contains custom-defined extend attributes, false otherwise
	 */
	public boolean containsExtendAttributes() {
		return !extendAttributes.isEmpty();
	}
	
	/**
	 * Convert result field into an string
	 * @param <T>
	 * @return result.
	 */
	public <T> T getResultAs(Class<T> t){
		return gson.fromJson(result, t);
	}	
	
	/**
	 * Convert result field into an ErrorResult object, null if no error
	 * @return error result.
	 */
	public Error getResultAsError(){
		if(!isError())
			return null;	
		
		return gson.fromJson(result, Error.class);
	}
	
	
	/**
	 * Convert result field into an string
	 * @return result.
	 */
	public String getResultAsString(){
		return result.toString();
	}
	
	@Getter @Setter
	public static class Error{
		private String message;
		private String errorClass;
		private String[] stack;
	}		
}
