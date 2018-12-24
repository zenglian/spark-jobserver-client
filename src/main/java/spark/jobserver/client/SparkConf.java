package spark.jobserver.client;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;

/**
 * Spark configurations.
 */
@Data
@Accessors(fluent = true)
public class SparkConf implements Mapping {
    private String appName;
    private long timeout = 500000;
    private String classPath;
    private boolean sync = false;
    private String context;
    @SerializedName("context-factory")
    private String contextFactory;
    @SerializedName("spark.yarn.jars")
    private String driverPath;
    @SerializedName("spark.executor.extraLibraryPath")
    private String executorPath;
}