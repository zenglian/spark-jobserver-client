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

/**
 * The exception indicates errors occurs when using instance 
 * of <code>IJobServerClient</code>.
 */
public class JobServerClientException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new <code>JobServerClientException</code> instance
	 * with the specified detail message. The cause is not initialized, and 
	 * may subsequently be initialized by a call to initCause.
	 * 
	 * @param message the detail message. The detail message is saved for 
	 *        later retrieval by the getMessage() method.
	 */
	public JobServerClientException(String message) {
		super(message);
	}
	
	/**
     * Constructs a new <code>JobServerClientException</code> instance
     * with the specified detail message and cause.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
	public JobServerClientException(String message, Throwable cause) {
		super(message, cause);
	}
}
