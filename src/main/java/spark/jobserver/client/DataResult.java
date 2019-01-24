
package spark.jobserver.client;

import lombok.Data;

@Data
public class DataResult {
  private Result result;

  @Data
  public static class Result {
    private String filename;
  }
}

