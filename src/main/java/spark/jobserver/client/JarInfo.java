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

import lombok.*;

/**
 * Presents the information of spark job jar files, when
 * calling <code>GET /jars</code> to a spark job server.
 *
 */
@Getter @Setter @AllArgsConstructor
public class JarInfo extends Pojo{
	private String name;
	private Date uploadedTime;
}
