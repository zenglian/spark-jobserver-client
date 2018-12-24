
package spark.jobserver.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Result {
    private String status;
    private String result;
}
