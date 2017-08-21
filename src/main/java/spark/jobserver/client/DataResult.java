
package spark.jobserver.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DataResult {
    private Result result;
    @Data
    public static class Result {
        private String filename;
    }
}

