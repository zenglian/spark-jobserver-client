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

import java.util.HashMap;
import java.util.Map;

import lombok.*;

/**
 * Presents the information of spark job configuration, when
 * calling <code>GET /jobs/&lt;jobId&gt;/config</code> to a 
 * spark job server.
 * 
 * <p>
 * The application used this <code>JobConfig</code> instance
 * should use the <code>getConfigs()</code> and parse the values
 * itself.
 *
 */

@Getter @Setter
public class JobConfig extends Pojo{
	private Map<String, Object> configs = new HashMap<String, Object>();
	
	void putConfigItem(String key, Object value) {
		this.configs.put(key, value);
	}
}
