package spark.jobserver.client;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Spark configurations.
 */
@Data
@Accessors(fluent = true)
public class SparkConst implements Mapping {
     // below are generated from https://spark.apache.org/docs/latest/configuration.html

    //Number of cores to use for the driver process, only in cluster mode.
    @SerializedName("spark.driver.cores")
    private long driverCores = 1;

    //Limit of total size of serialized results of all partitions for each Spark action (e.g. collect). Should be at
    // least 1M, or 0 for unlimited. Jobs will be aborted if the total size is above this limit. Having a high limit
    // may cause out-of-memory errors in driver (depends on spark.driver.memory and memory overhead of objects in
    // JVM). Setting a proper limit can protect the driver from out-of-memory errors.
    @SerializedName("spark.driver.maxResultSize")
    private String driverMaxResultSize = "1g";

    //Amount of memory to use for the driver process, i.e. where SparkContext is initialized. (e.g. 1g, 2g).
    @SerializedName("spark.driver.memory")
    private String driverMemory = "1g";

    //Amount of memory to use per executor process (e.g. 2g, 8g).
    @SerializedName("spark.executor.memory")
    private String executorMemory = "1g";

    //A comma-separated list of classes that implement SparkListener; when initializing SparkContext, instances of
    // these classes will be created and registered with Spark's listener bus. If a class has a single-argument
    // constructor that accepts a SparkConf, that constructor will be called; otherwise, a zero-argument constructor
    // will be called. If no valid constructor can be found, the SparkContext creation will fail with an exception.
    @SerializedName("spark.extraListeners")
    private String extraListeners;

    //Directory to use for "scratch" space in Spark, including map output files and RDDs that get stored on disk.
    // This should be on a fast, local disk in your system. It can also be a comma-separated list of multiple
    // directories on different disks. NOTE: In Spark 1.0 and later this will be overridden by SPARK_LOCAL_DIRS
    // (Standalone, Mesos) or LOCAL_DIRS (YARN) environment variables set by the cluster manager.
    @SerializedName("spark.local.dir")
    private String localDir = "/tmp";

    //Logs the effective SparkConf as INFO when a SparkContext is started.
    @SerializedName("spark.logConf")
    private boolean logConf = false;

    //The cluster manager to connect to. See the list of allowed master URL's.
    @SerializedName("spark.master")
    private String master;

    //The deploy mode of Spark driver program, either "client" or "cluster", Which means to launch driver program
    // locally ("client") or remotely ("cluster") on one of the nodes inside the cluster.
    @SerializedName("spark.submit.deployMode")
    private String submitDeployMode;

    //Application information that will be written into Yarn RM log/HDFS audit log when stillRunning on Yarn/HDFS.
    // Its length depends on the Hadoop configuration hadoop.caller.context.max.size. It should be concise, and
    // typically can have up to 50 characters.
    @SerializedName("spark.log.callerContext")
    private String logCallerContext;

    //If true, restarts the driver automatically if it fails with a non-zero exit status. Only has effect in Spark
    // standalone mode or Mesos cluster deploy mode.
    @SerializedName("spark.driver.supervise")
    private boolean driverSupervise = false;

    //Extra classpath entries to prepend to the classpath of the driver.
    @SerializedName("spark.driver.extraClassPath")
    private String driverExtraClassPath;

    //A string of extra JVM options to pass to the driver. For instance, GC settings or other logging. Note that it
    // is illegal to set maximum heap size (-Xmx) settings with this option. Maximum heap size settings can be set
    // with spark.driver.memory in the cluster mode and through the --driver-memory command line option in the client
    // mode.
    @SerializedName("spark.driver.extraJavaOptions")
    private String driverExtraJavaOptions;

    //Set a special library path to use when launching the driver JVM.
    @SerializedName("spark.driver.extraLibraryPath")
    private String driverExtraLibraryPath;

    //(Experimental) Whether to give user-added jars precedence over Spark's own jars when loading classes in the
    // driver. This feature can be used to mitigate conflicts between Spark's dependencies and user dependencies. It
    // is currently an experimental feature. This is used in cluster mode only.
    @SerializedName("spark.driver.userClassPathFirst")
    private boolean driverUserClassPathFirst = false;

    //Extra classpath entries to prepend to the classpath of executors. This exists primarily for
    // backwards-compatibility with older versions of Spark. Users typically should not need to set this option.
    @SerializedName("spark.executor.extraClassPath")
    private String executorExtraClassPath;

    //A string of extra JVM options to pass to executors. For instance, GC settings or other logging. Note that it is
    // illegal to set Spark properties or maximum heap size (-Xmx) settings with this option. Spark properties should
    // be set using a SparkConf object or the spark-defaults.conf file used with the spark-submit script. Maximum
    // heap size settings can be set with spark.executor.memory.
    @SerializedName("spark.executor.extraJavaOptions")
    private String executorExtraJavaOptions;

    //Set a special library path to use when launching executor JVM's.
    @SerializedName("spark.executor.extraLibraryPath")
    private String executorExtraLibraryPath;

    //Sets the number of latest rolling log files that are going to be retained by the system. Older log files will
    // be deleted. Disabled by default.
    @SerializedName("spark.executor.logs.rolling.maxRetainedFiles")
    private String executorLogsRollingMaxRetainedFiles;

    //Enable executor log compression. If it is enabled, the rolled executor logs will be compressed. Disabled by
    // default.
    @SerializedName("spark.executor.logs.rolling.enableCompression")
    private boolean executorLogsRollingEnableCompression = false;

    //Set the max size of the file in bytes by which the executor logs will be rolled over. Rolling is disabled by
    // default. See spark.executor.logs.rolling.maxRetainedFiles for automatic cleaning of old logs.
    @SerializedName("spark.executor.logs.rolling.maxSize")
    private String executorLogsRollingMaxSize;

    //Set the strategy of rolling of executor logs. By default it is disabled. It can be set to "time" (time-based
    // rolling) or "size" (size-based rolling). For "time", use spark.executor.logs.rolling.time.interval to set the
    // rolling interval. For "size", use spark.executor.logs.rolling.maxSize to set the maximum file size for rolling.
    @SerializedName("spark.executor.logs.rolling.strategy")
    private String executorLogsRollingStrategy;

    //Set the time interval by which the executor logs will be rolled over. Rolling is disabled by default. Valid
    // values are daily, hourly, minutely or any interval in seconds. See spark.executor.logs.rolling
    // .maxRetainedFiles for automatic cleaning of old logs.
    @SerializedName("spark.executor.logs.rolling.time.interval")
    private String executorLogsRollingTimeInterval = "daily";

    //(Experimental) Same functionality as spark.driver.userClassPathFirst, but applied to executor instances.
    @SerializedName("spark.executor.userClassPathFirst")
    private boolean executorUserClassPathFirst = false;

    //Add the environment variable specified by EnvironmentVariableName to the Executor process. The user can specify
    // multiple of these to set multiple environment variables.
    //@SerializedName("spark.executorEnv.[EnvironmentVariableName]")
    //TODO private String executorEnv;EnvironmentVariableName;

    //Regex to decide which Spark configuration properties and environment variables in driver and executor
    // environments contain sensitive information. When this regex matches a property key or value, the value is
    // redacted from the environment UI and various logs like YARN and event logs.
    @SerializedName("spark.redaction.regex")
    private String redactionRegex = "(?i)secret|password";

    //Enable profiling in Python worker, the profile result will show up by sc.show_profiles(), or it will be
    // displayed before the driver exiting. It also can be dumped into disk by sc.dump_profiles(path). If some of the
    // profile results had been displayed manually, they will not be displayed automatically before driver exiting.
    // By default the pyspark.profiler.BasicProfiler will be used, but this can be overridden by passing a profiler
    // class in as a parameter to the SparkContext constructor.
    @SerializedName("spark.python.profile")
    private boolean pythonProfile = false;

    //The directory which is used to dump the profile result before driver exiting. The results will be dumped as
    // separated file for each RDD. They can be loaded by ptats.Stats(). If this is specified, the profile result
    // will not be displayed automatically.
    @SerializedName("spark.python.profile.dump")
    private String pythonProfileDump;

    //Amount of memory to use per python worker process during aggregation, in the same format as JVM memory strings
    // (e.g. 512m, 2g). If the memory used during aggregation goes above this amount, it will spill the data into disks.
    @SerializedName("spark.python.worker.memory")
    private String pythonWorkerMemory = "512m";

    //Reuse Python worker or not. If yes, it will use a fixed number of Python workers, does not need to fork() a
    // Python process for every tasks. It will be very useful if there is large broadcast, then the broadcast will
    // not be needed to transferred from JVM to Python worker for every task.
    @SerializedName("spark.python.worker.reuse")
    private boolean pythonWorkerReuse = true;

    //Comma-separated list of files to be placed in the working directory of each executor.
    @SerializedName("spark.files")
    private String files = "";

    //Comma-separated list of .zip, .egg, or .py files to place on the PYTHONPATH for Python apps.
    @SerializedName("spark.submit.pyFiles")
    private String submitPyFiles = "";

    //Comma-separated list of local jars to include on the driver and executor classpaths.
    @SerializedName("spark.jars")
    private String jars = "";

    //Comma-separated list of Maven coordinates of jars to include on the driver and executor classpaths. The
    // coordinates should be groupId:artifactId:version. If spark.jars.ivySettings is given artifacts will be
    // resolved according to the configuration in the file, otherwise artifacts will be searched for in the local
    // maven repo, then maven central and finally any additional remote repositories given by the command-line option
    // --repositories. For more details, see Advanced Dependency Management.
    @SerializedName("spark.jars.packages")
    private String jarsPackages = "";

    //Comma-separated list of groupId:artifactId, to exclude while resolving the dependencies provided in spark.jars
    // .packages to avoid dependency conflicts.
    @SerializedName("spark.jars.excludes")
    private String jarsExcludes = "";

    //Path to specify the Ivy user directory, used for the local Ivy cache and package files from spark.jars.packages
    // . This will override the Ivy property ivy.default.ivy.user.dir which defaults to ~/.ivy2.
    @SerializedName("spark.jars.ivy")
    private String jarsIvy = "";

    //Path to an Ivy settings file to customize resolution of jars specified using spark.jars.packages instead of the
    // built-in defaults, such as maven central. Additional repositories given by the command-line option
    // --repositories will also be included. Useful for allowing Spark to resolve artifacts from behind a firewall e
    // .g. via an in-house artifact server like Artifactory. Details on the settings file format can be found at
    // http://ant.apache.org/ivy/history/latest-milestone/settings.html
    @SerializedName("spark.jars.ivySettings")
    private String jarsIvySettings = "";

    //Python binary executable to use for PySpark in driver. (default is spark.pyspark.python)
    @SerializedName("spark.pyspark.driver.python")
    private String pysparkDriverPython = "";

    //Python binary executable to use for PySpark in both driver and executors.
    @SerializedName("spark.pyspark.python")
    private String pysparkPython = "";

    //Maximum size of map outputs to fetch simultaneously from each reduce task. Since each output requires us to
    // create a buffer to receive it, this represents a fixed memory overhead per reduce task, so keep it small
    // unless you have a large amount of memory.
    @SerializedName("spark.reducer.maxSizeInFlight")
    private String reducerMaxSizeInFlight = "48m";

    //This configuration limits the number of remote requests to fetch blocks at any given point. When the number of
    // hosts in the cluster increase, it might lead to very large number of in-bound connections to one or more
    // nodes, causing the workers to fail under load. By allowing it to limit the number of fetch requests, this
    // scenario can be mitigated.
    @SerializedName("spark.reducer.maxReqsInFlight")
    private String reducerMaxReqsInFlight = "Int.MaxValue";

    //Whether to compress map output files. Generally a good idea. Compression will use spark.io.compression.codec.
    @SerializedName("spark.shuffle.compress")
    private boolean shuffleCompress = true;

    //Size of the in-memory buffer for each shuffle file output stream. These buffers reduce the number of disk seeks
    // and system calls made in creating intermediate shuffle files.
    @SerializedName("spark.shuffle.file.buffer")
    private String shuffleFileBuffer = "32k";

    //(Netty only) Fetches that fail due to IO-related exceptions are automatically retried if this is set to a
    // non-zero value. This retry logic helps stabilize large shuffles in the face of long GC pauses or transient
    // network connectivity issues.
    @SerializedName("spark.shuffle.io.maxRetries")
    private long shuffleIoMaxRetries = 3;

    //(Netty only) Connections between hosts are reused in order to reduce connection buildup for large clusters. For
    // clusters with many hard disks and few hosts, this may result in insufficient concurrency to saturate all
    // disks, and so users may consider increasing this value.
    @SerializedName("spark.shuffle.io.numConnectionsPerPeer")
    private long shuffleIoNumConnectionsPerPeer = 1;

    //(Netty only) Off-heap buffers are used to reduce garbage collection during shuffle and cache block transfer.
    // For environments where off-heap memory is tightly limited, users may wish to turn this off to force all
    // allocations from Netty to be on-heap.
    @SerializedName("spark.shuffle.io.preferDirectBufs")
    private boolean shuffleIoPreferDirectBufs = true;

    //(Netty only) How long to wait between retries of fetches. The maximum delay caused by retrying is 15 seconds by
    // default, calculated as maxRetries * retryWait.
    @SerializedName("spark.shuffle.io.retryWait")
    private String shuffleIoRetryWait = "5s";

    //Enables the external shuffle service. This service preserves the shuffle files written by executors so the
    // executors can be safely removed. This must be enabled if spark.dynamicAllocation.enabled is "true". The
    // external shuffle service must be set up in order to enable it. See dynamic allocation configuration and setup
    // documentation for more information.
    @SerializedName("spark.shuffle.service.enabled")
    private boolean shuffleServiceEnabled = false;

    //Port on which the external shuffle service will run.
    @SerializedName("spark.shuffle.service.port")
    private long shuffleServicePort = 7337;

    //Max number of entries to keep in the index cache of the shuffle service.
    @SerializedName("spark.shuffle.service.index.cache.entries")
    private long shuffleServiceIndexCacheEntries = 1024;

    //(Advanced) In the sort-based shuffle manager, avoid merge-sorting data if there is no map-side aggregation and
    // there are at most this many reduce partitions.
    @SerializedName("spark.shuffle.sort.bypassMergeThreshold")
    private long shuffleSortBypassMergeThreshold = 200;

    //Whether to compress data spilled during shuffles. Compression will use spark.io.compression.codec.
    @SerializedName("spark.shuffle.spill.compress")
    private boolean shuffleSpillCompress = true;

    //When we compress the size of shuffle blocks in HighlyCompressedMapStatus, we will record the size accurately if
    // it's above this config. This helps to prevent OOM by avoiding underestimating shuffle block size when fetch
    // shuffle blocks.
    @SerializedName("spark.shuffle.accurateBlockThreshold")
    private String shuffleAccurateBlockThreshold = "100 * 1024 * 1024";

    //Enable IO encryption. Currently supported by all modes except Mesos. It's recommended that RPC encryption be
    // enabled when using this feature.
    @SerializedName("spark.io.encryption.enabled")
    private boolean ioEncryptionEnabled = false;

    //IO encryption key size in bits. Supported values are 128, 192 and 256.
    @SerializedName("spark.io.encryption.keySizeBits")
    private long ioEncryptionKeySizeBits = 128;

    //The algorithm to use when generating the IO encryption key. The supported algorithms are described in the
    // KeyGenerator section of the Java Cryptography Architecture Standard Algorithm Name Documentation.
    @SerializedName("spark.io.encryption.keygen.algorithm")
    private String ioEncryptionKeygenAlgorithm = "HmacSHA1";

    //Whether to compress logged events, if spark.eventLog.enabled is true. Compression will use spark.io.compression
    // .codec.
    @SerializedName("spark.eventLog.compress")
    private boolean eventLogCompress = false;

    //Base directory in which Spark events are logged, if spark.eventLog.enabled is true. Within this base directory,
    // Spark creates a sub-directory for each application, and logs the events specific to the application in this
    // directory. Users may want to set this to a unified location like an HDFS directory so history files can be
    // read by the history server.
    @SerializedName("spark.eventLog.dir")
    private String eventLogDir = "file:///tmp/spark-events";

    //Whether to log Spark events, useful for reconstructing the Web UI after the application has finished.
    @SerializedName("spark.eventLog.enabled")
    private boolean eventLogEnabled = false;

    //Whether to run the web UI for the Spark application.
    @SerializedName("spark.ui.enabled")
    private boolean uiEnabled = true;

    //Allows jobs and stages to be killed from the web UI.
    @SerializedName("spark.ui.killEnabled")
    private boolean uiKillEnabled = true;

    //Port for your application's dashboard, which shows memory and workload data.
    @SerializedName("spark.ui.port")
    private long uiPort = 4040;

    //How many jobs the Spark UI and status APIs remember before garbage collecting. This is a target maximum, and
    // fewer elements may be retained in some circumstances.
    @SerializedName("spark.ui.retainedJobs")
    private long uiRetainedJobs = 1000;

    //How many stages the Spark UI and status APIs remember before garbage collecting. This is a target maximum, and
    // fewer elements may be retained in some circumstances.
    @SerializedName("spark.ui.retainedStages")
    private long uiRetainedStages = 1000;

    //How many tasks the Spark UI and status APIs remember before garbage collecting. This is a target maximum, and
    // fewer elements may be retained in some circumstances.
    @SerializedName("spark.ui.retainedTasks")
    private long uiRetainedTasks = 100000;

    //Enable running Spark Master as reverse proxy for worker and application UIs. In this mode, Spark master will
    // reverse proxy the worker and application UIs to enable access without requiring direct access to their hosts.
    // Use it with caution, as worker and application UI will not be accessible directly, you will only be able to
    // access them through spark master/proxy public URL. This setting affects all the workers and application UIs
    // running in the cluster and must be set on all the workers, drivers and masters.
    @SerializedName("spark.ui.reverseProxy")
    private boolean uiReverseProxy = false;

    //This is the URL where your proxy is running. This URL is for proxy which is running in front of Spark Master.
    // This is useful when running proxy for authentication e.g. OAuth proxy. Make sure this is a complete URL
    // including scheme (http/https) and port to reach your proxy.
    @SerializedName("spark.ui.reverseProxyUrl")
    private String uiReverseProxyUrl = "";

    //Show the progress bar in the console. The progress bar shows the progress of stages that run for longer than
    // 500ms. If multiple stages run at the same time, multiple progress bars will be displayed on the same line.
    @SerializedName("spark.ui.showConsoleProgress")
    private boolean uiShowConsoleProgress = true;

    //How many finished executors the Spark UI and status APIs remember before garbage collecting.
    @SerializedName("spark.worker.ui.retainedExecutors")
    private long workerUiRetainedExecutors = 1000;

    //How many finished drivers the Spark UI and status APIs remember before garbage collecting.
    @SerializedName("spark.worker.ui.retainedDrivers")
    private long workerUiRetainedDrivers = 1000;

    //How many finished executions the Spark UI and status APIs remember before garbage collecting.
    @SerializedName("spark.sql.ui.retainedExecutions")
    private long sqlUiRetainedExecutions = 1000;

    //How many dead executors the Spark UI and status APIs remember before garbage collecting.
    @SerializedName("spark.ui.retainedDeadExecutors")
    private long uiRetainedDeadExecutors = 100;

    //Whether to compress broadcast variables before sending them. Generally a good idea. Compression will use spark
    // .io.compression.codec.
    @SerializedName("spark.broadcast.compress")
    private boolean broadcastCompress = true;

    //The codec used to compress internal data such as RDD partitions, event log, broadcast variables and shuffle
    // outputs. By default, Spark provides three codecs: lz4, lzf, and snappy. You can also use fully qualified class
    // names to specify the codec, e.g. org.apache.spark.io.LZ4CompressionCodec, org.apache.spark.io
    // .LZFCompressionCodec, and org.apache.spark.io.SnappyCompressionCodec.
    @SerializedName("spark.io.compression.codec")
    private String ioCompressionCodec = "lz4";

    //Block size used in LZ4 compression, in the case when LZ4 compression codec is used. Lowering this block size
    // will also lower shuffle memory usage when LZ4 is used.
    @SerializedName("spark.io.compression.lz4.blockSize")
    private String ioCompressionLz4BlockSize = "32k";

    //Block size used in Snappy compression, in the case when Snappy compression codec is used. Lowering this block
    // size will also lower shuffle memory usage when Snappy is used.
    @SerializedName("spark.io.compression.snappy.blockSize")
    private String ioCompressionSnappyBlockSize = "32k";

    //If you use Kryo serialization, give a comma-separated list of custom class names to register with Kryo. See the
    // tuning guide for more details.
    @SerializedName("spark.kryo.classesToRegister")
    private String kryoClassesToRegister;

    //Whether to track references to the same object when serializing data with Kryo, which is necessary if your
    // object graphs have loops and useful for efficiency if they contain multiple copies of the same object. Can be
    // disabled to improve performance if you know this is not the case.
    @SerializedName("spark.kryo.referenceTracking")
    private boolean kryoReferenceTracking = true;

    //Whether to require registration with Kryo. If set to 'true', Kryo will throw an exception if an unregistered
    // class is serialized. If set to false (the default), Kryo will write unregistered class names along with each
    // object. Writing class names can cause significant performance overhead, so enabling this option can enforce
    // strictly that a user has not omitted classes from registration.
    @SerializedName("spark.kryo.registrationRequired")
    private boolean kryoRegistrationRequired = false;

    //If you use Kryo serialization, give a comma-separated list of classes that register your custom classes with
    // Kryo. This property is useful if you need to register your classes in a custom way, e.g. to specify a custom
    // field serializer. Otherwise spark.kryo.classesToRegister is simpler. It should be set to classes that extend
    // KryoRegistrator. See the tuning guide for more details.
    @SerializedName("spark.kryo.registrator")
    private String kryoRegistrator;

    //Whether to use unsafe based Kryo serializer. Can be substantially faster by using Unsafe Based IO.
    @SerializedName("spark.kryo.unsafe")
    private boolean kryoUnsafe = false;

    //Maximum allowable size of Kryo serialization buffer. This must be larger than any object you attempt to
    // serialize and must be less than 2048m. Increase this if you get a "buffer limit exceeded" exception inside Kryo.
    @SerializedName("spark.kryoserializer.buffer.max")
    private String kryoserializerBufferMax = "64m";

    //Initial size of Kryo's serialization buffer. Note that there will be one buffer per core on each worker. This
    // buffer will grow up to spark.kryoserializer.buffer.max if needed.
    @SerializedName("spark.kryoserializer.buffer")
    private String kryoserializerBuffer = "64k";

    //Whether to compress serialized RDD partitions (e.g. for StorageLevel.MEMORY_ONLY_SER in Java and Scala or
    // StorageLevel.MEMORY_ONLY in Python). Can save substantial space at the cost of some extra CPU time.
    // Compression will use spark.io.compression.codec.
    @SerializedName("spark.rdd.compress")
    private boolean rddCompress = false;

    //When serializing using org.apache.spark.serializer.JavaSerializer, the serializer caches objects to prevent
    // writing redundant data, however that stops garbage collection of those objects. By calling 'reset' you flush
    // that info from the serializer, and allow old objects to be collected. To turn off this periodic reset set it
    // to -1. By default it will reset the serializer every 100 objects.
    @SerializedName("spark.serializer.objectStreamReset")
    private long serializerObjectStreamReset = 100;

    //Fraction of (heap space - 300MB) used for execution and storage. The lower this is, the more frequently spills
    // and cached data eviction occur. The purpose of this config is to set aside memory for internal metadata, user
    // data structures, and imprecise size estimation in the case of sparse, unusually large records. Leaving this at
    // the default value is recommended. For more detail, including important information about correctly tuning JVM
    // garbage collection when increasing this value, see this description.
    @SerializedName("spark.memory.fraction")
    private double memoryFraction = 0.6;

    //Amount of storage memory immune to eviction, expressed as a fraction of the size of the region set aside by
    // s​park.memory.fraction. The higher this is, the less working memory may be available to execution and tasks
    // may spill to disk more often. Leaving this at the default value is recommended. For more detail, see this
    // description.
    @SerializedName("spark.memory.storageFraction")
    private double memoryStorageFraction = 0.5;

    //If true, Spark will attempt to use off-heap memory for certain operations. If off-heap memory use is enabled,
    // then spark.memory.offHeap.size must be positive.
    @SerializedName("spark.memory.offHeap.enabled")
    private boolean memoryOffHeapEnabled = false;

    //The absolute amount of memory in bytes which can be used for off-heap allocation. This setting has no impact on
    // heap memory usage, so if your executors' total memory consumption must fit within some hard limit then be sure
    // to shrink your JVM heap size accordingly. This must be set to a positive value when spark.memory.offHeap
    // .enabled=true.
    @SerializedName("spark.memory.offHeap.size")
    private long memoryOffHeapSize = 0;

    //​Whether to enable the legacy memory management mode used in Spark 1.5 and before. The legacy mode rigidly
    // partitions the heap space into fixed-size regions, potentially leading to excessive spilling if the
    // application was not tuned. The following deprecated memory fraction configurations are not read unless this is
    // enabled: spark.shuffle.memoryFraction
    @SerializedName("spark.memory.useLegacyMode")
    private boolean memoryUseLegacyMode = false;

    //(deprecated) This is read only if spark.memory.useLegacyMode is enabled. Fraction of Java heap to use for
    // aggregation and cogroups during shuffles. At any given time, the collective size of all in-memory maps used
    // for shuffles is bounded by this limit, beyond which the contents will begin to spill to disk. If spills are
    // often, consider increasing this value at the expense of spark.storage.memoryFraction.
    @SerializedName("spark.shuffle.memoryFraction")
    private double shuffleMemoryFraction = 0.2;

    //(deprecated) This is read only if spark.memory.useLegacyMode is enabled. Fraction of Java heap to use for
    // Spark's memory cache. This should not be larger than the "old" generation of objects in the JVM, which by
    // default is given 0.6 of the heap, but you can increase it if you configure your own old generation size.
    @SerializedName("spark.storage.memoryFraction")
    private double storageMemoryFraction = 0.6;

    //(deprecated) This is read only if spark.memory.useLegacyMode is enabled. Fraction of spark.storage
    // .memoryFraction to use for unrolling blocks in memory. This is dynamically allocated by dropping existing
    // blocks when there is not enough free storage space to unroll the new block in its entirety.
    @SerializedName("spark.storage.unrollFraction")
    private double storageUnrollFraction = 0.2;

    //Enables proactive block replication for RDD blocks. Cached RDD block replicas lost due to executor failures are
    // replenished if there are any existing available replicas. This tries to get the replication level of the block
    // to the initial number.
    @SerializedName("spark.storage.replication.proactive")
    private boolean storageReplicationProactive = false;

    //Size of each piece of a block for TorrentBroadcastFactory. Too large a value decreases parallelism during
    // broadcast (makes it slower); however, if it is too small, BlockManager might take a performance hit.
    @SerializedName("spark.broadcast.blockSize")
    private String broadcastBlockSize = "4m";

    //The number of cores to use on each executor. In standalone and Mesos coarse-grained modes, setting this
    // parameter allows an application to run multiple executors on the same worker, provided that there are enough
    // cores on that worker. Otherwise, only one executor per application will run on each worker.
    @SerializedName("spark.executor.cores")
    private int executorCores = 1; //"1 in YARN mode, all the available cores on the worker in standalone and Mesos
    // coarse-grained modes.";

    //Interval between each executor's heartbeats to the driver. Heartbeats let the driver know that the executor is
    // still alive and update it with metrics for in-progress tasks. spark.executor.heartbeatInterval should be
    // significantly less than spark.network.timeout
    @SerializedName("spark.executor.heartbeatInterval")
    private String executorHeartbeatInterval = "10s";

    //Communication timeout to use when fetching files added through SparkContext.addFile() from the driver.
    @SerializedName("spark.files.fetchTimeout")
    private String filesFetchTimeout = "60s";

    //If set to true (default), file fetching will use a local cache that is shared by executors that belong to the
    // same application, which can improve task launching performance when stillRunning many executors on the same
    // host. If set to false, these caching optimizations will be disabled and all executors will fetch their own
    // copies of files. This optimization may be disabled in order to use Spark local directories that reside on NFS
    // filesystems (see SPARK-6313 for more details).
    @SerializedName("spark.files.useFetchCache")
    private boolean filesUseFetchCache = true;

    //Whether to overwrite files added through SparkContext.addFile() when the target file exists and its contents do
    // not match those of the source.
    @SerializedName("spark.files.overwrite")
    private boolean filesOverwrite = false;

    //The maximum number of bytes to pack into a single partition when reading files.
    @SerializedName("spark.files.maxPartitionBytes")
    private String filesMaxPartitionBytes = "134217728 (128 MB)";

    //The estimated cost to open a file, measured by the number of bytes could be scanned in the same time. This is
    // used when putting multiple files into a partition. It is better to over estimate, then the partitions with
    // small files will be faster than partitions with bigger files.
    @SerializedName("spark.files.openCostInBytes")
    private String filesOpenCostInBytes = "4194304 (4 MB)";

    //If set to true, clones a new Hadoop Configuration object for each task. This option should be enabled to work
    // around Configuration thread-safety issues (see SPARK-2546 for more details). This is disabled by default in
    // order to avoid unexpected performance regressions for jobs that are not affected by these issues.
    @SerializedName("spark.hadoop.cloneConf")
    private boolean hadoopCloneConf = false;

    //If set to true, validates the output specification (e.g. checking if the output directory already exists) used
    // in saveAsHadoopFile and other variants. This can be disabled to silence exceptions due to pre-existing output
    // directories. We recommend that users do not disable this except if trying to achieve compatibility with
    // previous versions of Spark. Simply use Hadoop's FileSystem API to delete output directories by hand. This
    // setting is ignored for jobs generated through Spark Streaming's StreamingContext, since data may need to be
    // rewritten to pre-existing output directories during checkpoint recovery.
    @SerializedName("spark.hadoop.validateOutputSpecs")
    private boolean hadoopValidateOutputSpecs = true;

    //Size of a block above which Spark memory maps when reading a block from disk. This prevents Spark from memory
    // mapping very small blocks. In general, memory mapping has high overhead for blocks close to or below the page
    // size of the operating system.
    @SerializedName("spark.storage.memoryMapThreshold")
    private String storageMemoryMapThreshold = "2m";

    //The file output committer algorithm version, valid algorithm version number: 1 or 2. Version 2 may have better
    // performance, but version 1 may handle failures better in certain situations, as per MAPREDUCE-4815.
    @SerializedName("spark.hadoop.mapreduce.fileoutputcommitter.algorithm.version")
    private long hadoopMapreduceFileoutputcommitterAlgorithmVersion = 1;

    //Maximum message size (in MB) to allow in "control plane" communication; generally only applies to map output
    // size information sent between executors and the driver. Increase this if you are stillRunning jobs with many
    // thousands of map and reduce tasks and see messages about the RPC message size.
    @SerializedName("spark.rpc.message.maxSize")
    private long rpcMessageMaxSize = 128;

    //Port for all block managers to listen on. These exist on both the driver and the executors.
    @SerializedName("spark.blockManager.port")
    private String blockManagerPort = "(random)";

    //Driver-specific port for the block manager to listen on, for cases where it cannot use the same configuration
    // as executors.
    @SerializedName("spark.driver.blockManager.port")
    private String driverBlockManagerPort = "(value of spark.blockManager.port)";

    //Hostname or IP address where to bind listening sockets. This config overrides the SPARK_LOCAL_IP environment
    // variable (see below).
    @SerializedName("spark.driver.bindAddress")
    private String driverBindAddress = "(value of spark.driver.host)";

    //Hostname or IP address for the driver. This is used for communicating with the executors and the standalone
    // Master.
    @SerializedName("spark.driver.host")
    private String driverHost = "(local hostname)";

    //Port for the driver to listen on. This is used for communicating with the executors and the standalone Master.
    @SerializedName("spark.driver.port")
    private String driverPort = "(random)";

    //Default timeout for all network interactions. This config will be used in place of spark.core.connection.ack
    // .wait.timeout, spark.storage.blockManagerSlaveTimeoutMs, spark.shuffle.io.connectionTimeout, spark.rpc
    // .askTimeout or spark.rpc.lookupTimeout if they are not configured.
    @SerializedName("spark.network.timeout")
    private String networkTimeout = "120s";

    //Maximum number of retries when binding to a port before giving up. When a port is given a specific value (non
    // 0), each subsequent retry will increment the port used in the previous attempt by 1 before retrying. This
    // essentially allows it to try a range of ports from the start port specified to port + maxRetries.
    @SerializedName("spark.port.maxRetries")
    private long portMaxRetries = 16;

    //Number of times to retry before an RPC task gives up. An RPC task will run at most times of this number.
    @SerializedName("spark.rpc.numRetries")
    private long rpcNumRetries = 3;

    //Duration for an RPC ask operation to wait before retrying.
    @SerializedName("spark.rpc.retry.wait")
    private String rpcRetryWait = "3s";

    //Duration for an RPC ask operation to wait before timing out.
    @SerializedName("spark.rpc.askTimeout")
    private String rpcAskTimeout = "spark.network.timeout";

    //Duration for an RPC remote endpoint lookup operation to wait before timing out.
    @SerializedName("spark.rpc.lookupTimeout")
    private String rpcLookupTimeout = "120s";

    //When stillRunning on a standalone deploy cluster or a Mesos cluster in "coarse-grained" sharing mode, the
    // maximum amount of CPU cores to request for the application from across the cluster (not from each machine). If
    // not set, the default will be spark.deploy.defaultCores on Spark's standalone cluster manager, or infinite (all
    // available cores) on Mesos.
    @SerializedName("spark.cores.max")
    private String coresMax = "(not set)";

    //How long to wait to launch a data-local task before giving up and launching it on a less-local node. The same
    // wait will be used to step through multiple locality levels (process-local, node-local, rack-local and then
    // any). It is also possible to customize the waiting time for each level by setting spark.locality.wait.node,
    // etc. You should increase this setting if your tasks are long and see poor locality, but the default usually
    // works well.
    @SerializedName("spark.locality.wait")
    private String localityWait = "3s";

    //Customize the locality wait for node locality. For example, you can set this to 0 to skip node locality and
    // search immediately for rack locality (if your cluster has rack information).
    @SerializedName("spark.locality.wait.node")
    private String localityWaitNode = "spark.locality.wait";

    //Customize the locality wait for process locality. This affects tasks that attempt to access cached data in a
    // particular executor process.
    @SerializedName("spark.locality.wait.process")
    private String localityWaitProcess = "spark.locality.wait";

    //Customize the locality wait for rack locality.
    @SerializedName("spark.locality.wait.rack")
    private String localityWaitRack = "spark.locality.wait";

    //Maximum amount of time to wait for resources to register before scheduling begins.
    @SerializedName("spark.scheduler.maxRegisteredResourcesWaitingTime")
    private String schedulerMaxRegisteredResourcesWaitingTime = "30s";

    //The minimum ratio of registered resources (registered resources / total expected resources) (resources are
    // executors in yarn mode, CPU cores in standalone mode and Mesos coarsed-grained mode ['spark.cores.max' value
    // is total expected resources for Mesos coarse-grained mode] ) to wait for before scheduling begins. Specified
    // as a double between 0.0 and 1.0. Regardless of whether the minimum ratio of resources has been reached, the
    // maximum amount of time it will wait before scheduling begins is controlled by config spark.scheduler
    // .maxRegisteredResourcesWaitingTime.
    @SerializedName("spark.scheduler.minRegisteredResourcesRatio")
    private String schedulerMinRegisteredResourcesRatio = "0.8 for YARN mode; 0.0 for standalone mode and Mesos " +
            "coarse-grained mode";

    //The scheduling mode between jobs submitted to the same SparkContext. Can be set to FAIR to use fair sharing
    // instead of queueing jobs one after another. Useful for multi-user services.
    @SerializedName("spark.scheduler.mode")
    private String schedulerMode = "FIFO";

    //The interval length for the scheduler to revive the worker resource offers to run tasks.
    @SerializedName("spark.scheduler.revive.interval")
    private String schedulerReviveInterval = "1s";

    //If set to "true", prevent Spark from scheduling tasks on executors that have been blacklisted due to too many
    // task failures. The blacklisting algorithm can be further controlled by the other "spark.blacklist"
    // configuration options.
    @SerializedName("spark.blacklist.enabled")
    private boolean blacklistEnabled = false;

    //(Experimental) How long a node or executor is blacklisted for the entire application, before it is
    // unconditionally removed from the blacklist to attempt stillRunning new tasks.
    @SerializedName("spark.blacklist.timeout")
    private String blacklistTimeout = "1h";

    //(Experimental) For a given task, how many times it can be retried on one executor before the executor is
    // blacklisted for that task.
    @SerializedName("spark.blacklist.task.maxTaskAttemptsPerExecutor")
    private long blacklistTaskMaxTaskAttemptsPerExecutor = 1;

    //(Experimental) For a given task, how many times it can be retried on one node, before the entire node is
    // blacklisted for that task.
    @SerializedName("spark.blacklist.task.maxTaskAttemptsPerNode")
    private long blacklistTaskMaxTaskAttemptsPerNode = 2;

    //(Experimental) How many different tasks must fail on one executor, within one stage, before the executor is
    // blacklisted for that stage.
    @SerializedName("spark.blacklist.stage.maxFailedTasksPerExecutor")
    private long blacklistStageMaxFailedTasksPerExecutor = 2;

    //(Experimental) How many different executors are marked as blacklisted for a given stage, before the entire node
    // is marked as failed for the stage.
    @SerializedName("spark.blacklist.stage.maxFailedExecutorsPerNode")
    private long blacklistStageMaxFailedExecutorsPerNode = 2;

    //(Experimental) How many different tasks must fail on one executor, in successful task sets, before the executor
    // is blacklisted for the entire application. Blacklisted executors will be automatically added back to the pool
    // of available resources after the timeout specified by spark.blacklist.timeout. Note that with dynamic
    // allocation, though, the executors may get marked as idle and be reclaimed by the cluster manager.
    @SerializedName("spark.blacklist.application.maxFailedTasksPerExecutor")
    private long blacklistApplicationMaxFailedTasksPerExecutor = 2;

    //(Experimental) How many different executors must be blacklisted for the entire application, before the node is
    // blacklisted for the entire application. Blacklisted nodes will be automatically added back to the pool of
    // available resources after the timeout specified by spark.blacklist.timeout. Note that with dynamic allocation,
    // though, the executors on the node may get marked as idle and be reclaimed by the cluster manager.
    @SerializedName("spark.blacklist.application.maxFailedExecutorsPerNode")
    private long blacklistApplicationMaxFailedExecutorsPerNode = 2;

    //(Experimental) If set to "true", allow Spark to automatically kill, and attempt to re-create, executors when
    // they are blacklisted. Note that, when an entire node is added to the blacklist, all of the executors on that
    // node will be killed.
    @SerializedName("spark.blacklist.killBlacklistedExecutors")
    private boolean blacklistKillBlacklistedExecutors = false;

    //If set to "true", performs speculative execution of tasks. This means if one or more tasks are stillRunning
    // slowly in a stage, they will be re-launched.
    @SerializedName("spark.speculation")
    private boolean speculation = false;

    //How often Spark will check for tasks to speculate.
    @SerializedName("spark.speculation.interval")
    private String speculationInterval = "100ms";

    //How many times slower a task is than the median to be considered for speculation.
    @SerializedName("spark.speculation.multiplier")
    private double speculationMultiplier = 1.5;

    //Fraction of tasks which must be complete before speculation is enabled for a particular stage.
    @SerializedName("spark.speculation.quantile")
    private double speculationQuantile = 0.75;

    //Number of cores to allocate for each task.
    @SerializedName("spark.task.cpus")
    private long taskCpus = 1;

    //Number of failures of any particular task before giving up on the job. The total number of failures spread
    // across different tasks will not cause the job to fail; a particular task has to fail this number of attempts.
    // Should be greater than or equal to 1. Number of allowed retries = this value - 1.
    @SerializedName("spark.task.maxFailures")
    private long taskMaxFailures = 4;

    //Enables monitoring of killed / interrupted tasks. When set to true, any task which is killed will be monitored
    // by the executor until that task actually finishes executing. See the other spark.task.reaper.* configurations
    // for details on how to control the exact behavior of this monitoring. When set to false (the default), task
    // killing will use an older code path which lacks such monitoring.
    @SerializedName("spark.task.reaper.enabled")
    private boolean taskReaperEnabled = false;

    //When spark.task.reaper.enabled = true, this setting controls the frequency at which executors will poll the
    // status of killed tasks. If a killed task is still stillRunning when polled then a warning will be logged and,
    // by default, a thread-dump of the task will be logged (this thread dump can be disabled via the spark.task
    // .reaper.threadDump setting, which is documented below).
    @SerializedName("spark.task.reaper.pollingInterval")
    private String taskReaperPollingInterval = "10s";

    //When spark.task.reaper.enabled = true, this setting controls whether task thread dumps are logged during
    // periodic polling of killed tasks. Set this to false to disable collection of thread dumps.
    @SerializedName("spark.task.reaper.threadDump")
    private boolean taskReaperThreadDump = true;

    //When spark.task.reaper.enabled = true, this setting specifies a timeout after which the executor JVM will kill
    // itself if a killed task has not stopped stillRunning. The default value, -1, disables this mechanism and
    // prevents the executor from self-destructing. The purpose of this setting is to act as a safety-net to prevent
    // runaway uncancellable tasks from rendering an executor unusable.
    @SerializedName("spark.task.reaper.killTimeout")
    private long taskReaperKillTimeout = -1;

    //Number of consecutive stage attempts allowed before a stage is aborted.
    @SerializedName("spark.stage.maxConsecutiveAttempts")
    private long stageMaxConsecutiveAttempts = 4;

    //Whether to use dynamic resource allocation, which scales the number of executors registered with this
    // application up and down based on the workload. For more detail, see the description here.
    @SerializedName("spark.dynamicAllocation.enabled")
    private boolean dynamicAllocationEnabled = false;

    //If dynamic allocation is enabled and an executor has been idle for more than this duration, the executor will
    // be removed. For more detail, see this description.
    @SerializedName("spark.dynamicAllocation.executorIdleTimeout")
    private String dynamicAllocationExecutorIdleTimeout = "60s";

    //If dynamic allocation is enabled and an executor which has cached data blocks has been idle for more than this
    // duration, the executor will be removed. For more details, see this description.
    @SerializedName("spark.dynamicAllocation.cachedExecutorIdleTimeout")
    private String dynamicAllocationCachedExecutorIdleTimeout = "infinity";

    //Initial number of executors to run if dynamic allocation is enabled.
    @SerializedName("spark.dynamicAllocation.initialExecutors")
    private String dynamicAllocationInitialExecutors = "spark.dynamicAllocation.minExecutors";

    //Upper bound for the number of executors if dynamic allocation is enabled.
    @SerializedName("spark.dynamicAllocation.maxExecutors")
    private String dynamicAllocationMaxExecutors = "infinity";

    //Lower bound for the number of executors if dynamic allocation is enabled.
    @SerializedName("spark.dynamicAllocation.minExecutors")
    private long dynamicAllocationMinExecutors = 0;

    //If dynamic allocation is enabled and there have been pending tasks backlogged for more than this duration, new
    // executors will be requested. For more detail, see this description.
    @SerializedName("spark.dynamicAllocation.schedulerBacklogTimeout")
    private String dynamicAllocationSchedulerBacklogTimeout = "1s";

    //Same as spark.dynamicAllocation.schedulerBacklogTimeout, but used only for subsequent executor requests. For
    // more detail, see this description.
    @SerializedName("spark.dynamicAllocation.sustainedSchedulerBacklogTimeout")
    private String dynamicAllocationSustainedSchedulerBacklogTimeout = "schedulerBacklogTimeout";

    //Whether Spark acls should be enabled. If enabled, this checks to see if the user has access permissions to view
    // or modify the job. Note this requires the user to be known, so if the user comes across as null no checks are
    // done. Filters can be used with the UI to authenticate and set the user.
    @SerializedName("spark.acls.enable")
    private boolean aclsEnable = false;

    //Comma separated list of users/administrators that have view and modify access to all Spark jobs. This can be
    // used if you run on a shared cluster and have a set of administrators or devs who help debug when things do not
    // work. Putting a "*" in the list means any user can have the privilege of admin.
    @SerializedName("spark.admin.acls")
    private String adminAcls;

    //Comma separated list of groups that have view and modify access to all Spark jobs. This can be used if you have
    // a set of administrators or developers who help maintain and debug the underlying infrastructure. Putting a "*"
    // in the list means any user in any group can have the privilege of admin. The user groups are obtained from the
    // instance of the groups mapping provider specified by spark.user.groups.mapping. Check the entry spark.user
    // .groups.mapping for more details.
    @SerializedName("spark.admin.acls.groups")
    private String adminAclsGroups;

    //The list of groups for a user are determined by a group mapping service defined by the trait org.apache.spark
    // .security.GroupMappingServiceProvider which can configured by this property. A default unix shell based
    // implementation is provided org.apache.spark.security.ShellBasedGroupsMappingProvider which can be specified to
    // resolve a list of groups for a user. Note: This implementation supports only a Unix/Linux based environment.
    // Windows environment is currently not supported. However, a new platform/protocol can be supported by
    // implementing the trait org.apache.spark.security.GroupMappingServiceProvider.
    @SerializedName("spark.user.groups.mapping")
    private String userGroupsMapping = "org.apache.spark.security.ShellBasedGroupsMappingProvider";

    //Whether Spark authenticates its internal connections. See spark.authenticate.secret if not stillRunning on YARN.
    @SerializedName("spark.authenticate")
    private boolean authenticate = false;

    //Set the secret key used for Spark to authenticate between components. This needs to be set if not stillRunning
    // on YARN and authentication is enabled.
    @SerializedName("spark.authenticate.secret")
    private String authenticateSecret;

    //Enable encryption using the commons-crypto library for RPC and block transfer service. Requires spark
    // .authenticate to be enabled.
    @SerializedName("spark.network.crypto.enabled")
    private boolean networkCryptoEnabled = false;

    //The length in bits of the encryption key to generate. Valid values are 128, 192 and 256.
    @SerializedName("spark.network.crypto.keyLength")
    private long networkCryptoKeyLength = 128;

    //The key factory algorithm to use when generating encryption keys. Should be one of the algorithms supported by
    // the javax.crypto.SecretKeyFactory class in the JRE being used.
    @SerializedName("spark.network.crypto.keyFactoryAlgorithm")
    private String networkCryptoKeyFactoryAlgorithm = "PBKDF2WithHmacSHA1";

    //Whether to fall back to SASL authentication if authentication fails using Spark's internal mechanism. This is
    // useful when the application is connecting to old shuffle services that do not support the internal Spark
    // authentication protocol. On the server side, this can be used to block older clients from authenticating
    // against a new shuffle service.
    @SerializedName("spark.network.crypto.saslFallback")
    private boolean networkCryptoSaslFallback = true;

    //Configuration values for the commons-crypto library, such as which cipher implementations to use. The config
    // name should be the name of commons-crypto configuration without the "commons.crypto" prefix.
    @SerializedName("spark.network.crypto.config.*")
    private String networkCryptoConfig;

    //Enable encrypted communication when authentication is enabled. This is supported by the block transfer service
    // and the RPC endpoints.
    @SerializedName("spark.authenticate.enableSaslEncryption")
    private boolean authenticateEnableSaslEncryption = false;

    //Disable unencrypted connections for services that support SASL authentication.
    @SerializedName("spark.network.sasl.serverAlwaysEncrypt")
    private boolean networkSaslServerAlwaysEncrypt = false;

    //How long for the connection to wait for ack to occur before timing out and giving up. To avoid unwilling
    // timeout caused by long pause like GC, you can set larger value.
    @SerializedName("spark.core.connection.ack.wait.timeout")
    private String coreConnectionAckWaitTimeout = "spark.network.timeout";

    //Comma separated list of users that have modify access to the Spark job. By default only the user that started
    // the Spark job has access to modify it (kill it for example). Putting a "*" in the list means any user can have
    // access to modify it.
    @SerializedName("spark.modify.acls")
    private String modifyAcls;

    //Comma separated list of groups that have modify access to the Spark job. This can be used if you have a set of
    // administrators or developers from the same team to have access to control the job. Putting a "*" in the list
    // means any user in any group has the access to modify the Spark job. The user groups are obtained from the
    // instance of the groups mapping provider specified by spark.user.groups.mapping. Check the entry spark.user
    // .groups.mapping for more details.
    @SerializedName("spark.modify.acls.groups")
    private String modifyAclsGroups;

    //Comma separated list of filter class names to apply to the Spark web UI. The filter should be a standard javax
    // servlet Filter. Parameters to each filter can also be specified by setting a java system property of:
    @SerializedName("spark.ui.filters")
    private String uiFilters;

    //Comma separated list of users that have view access to the Spark web ui. By default only the user that started
    // the Spark job has view access. Putting a "*" in the list means any user can have view access to this Spark job.
    @SerializedName("spark.ui.view.acls")
    private String uiViewAcls;

    //Comma separated list of groups that have view access to the Spark web ui to view the Spark Job details. This
    // can be used if you have a set of administrators or developers or users who can monitor the Spark job submitted
    // . Putting a "*" in the list means any user in any group can view the Spark job details on the Spark web ui.
    // The user groups are obtained from the instance of the groups mapping provider specified by spark.user.groups
    // .mapping. Check the entry spark.user.groups.mapping for more details.
    @SerializedName("spark.ui.view.acls.groups")
    private String uiViewAclsGroups;

    //Whether to enable SSL connections on all supported protocols.
    @SerializedName("spark.ssl.enabled")
    private boolean sslEnabled = false;

    //The port where the SSL service will listen on.
    //@SerializedName("spark.ssl.[namespace].port")
    //TODO private String ssl;namespaceport;

    //A comma separated list of ciphers. The specified ciphers must be supported by JVM. The reference list of
    // protocols one can find on this page. Note: If not set, it will use the default cipher suites of JVM.
    @SerializedName("spark.ssl.enabledAlgorithms")
    private String sslEnabledAlgorithms;

    //A password to the private key in key-store.
    @SerializedName("spark.ssl.keyPassword")
    private String sslKeyPassword;

    //A path to a key-store file. The path can be absolute or relative to the directory where the component is
    // started in.
    @SerializedName("spark.ssl.keyStore")
    private String sslKeyStore;

    //A password to the key-store.
    @SerializedName("spark.ssl.keyStorePassword")
    private String sslKeyStorePassword;

    //The type of the key-store.
    @SerializedName("spark.ssl.keyStoreType")
    private String sslKeyStoreType = "JKS";

    //A protocol name. The protocol must be supported by JVM. The reference list of protocols one can find on this page.
    @SerializedName("spark.ssl.protocol")
    private String sslProtocol;

    //Set true if SSL needs client authentication.
    @SerializedName("spark.ssl.needClientAuth")
    private boolean sslNeedClientAuth = false;

    //A path to a trust-store file. The path can be absolute or relative to the directory where the component is
    // started in.
    @SerializedName("spark.ssl.trustStore")
    private String sslTrustStore;

    //A password to the trust-store.
    @SerializedName("spark.ssl.trustStorePassword")
    private String sslTrustStorePassword;

    //The type of the trust-store.
    @SerializedName("spark.ssl.trustStoreType")
    private String sslTrustStoreType = "JKS";

    //Enables or disables Spark Streaming's internal backpressure mechanism (since 1.5). This enables the Spark
    // Streaming to control the receiving rate based on the current batch scheduling delays and processing times so
    // that the system receives only as fast as the system can process. Internally, this dynamically sets the maximum
    // receiving rate of receivers. This rate is upper bounded by the values spark.streaming.receiver.maxRate and
    // spark.streaming.kafka.maxRatePerPartition if they are set (see below).
    @SerializedName("spark.streaming.backpressure.enabled")
    private boolean streamingBackpressureEnabled = false;

    //This is the initial maximum receiving rate at which each receiver will receive data for the first batch when
    // the backpressure mechanism is enabled.
    @SerializedName("spark.streaming.backpressure.initialRate")
    private String streamingBackpressureInitialRate = "not set";

    //Interval at which data received by Spark Streaming receivers is chunked into blocks of data before storing them
    // in Spark. Minimum recommended - 50 ms. See the performance tuning section in the Spark Streaming programing
    // guide for more details.
    @SerializedName("spark.streaming.blockInterval")
    private String streamingBlockInterval = "200ms";

    //Maximum rate (number of records per second) at which each receiver will receive data. Effectively, each stream
    // will consume at most this number of records per second. Setting this configuration to 0 or a negative number
    // will put no limit on the rate. See the deployment guide in the Spark Streaming programing guide for mode details.
    @SerializedName("spark.streaming.receiver.maxRate")
    private String streamingReceiverMaxRate = "not set";

    //Enable write ahead logs for receivers. All the input data received through receivers will be saved to write
    // ahead logs that will allow it to be recovered after driver failures. See the deployment guide in the Spark
    // Streaming programing guide for more details.
    @SerializedName("spark.streaming.receiver.writeAheadLog.enable")
    private boolean streamingReceiverWriteAheadLogEnable = false;

    //Force RDDs generated and persisted by Spark Streaming to be automatically unpersisted from Spark's memory. The
    // raw input data received by Spark Streaming is also automatically cleared. Setting this to false will allow the
    // raw data and persisted RDDs to be accessible outside the streaming application as they will not be cleared
    // automatically. But it comes at the cost of higher memory usage in Spark.
    @SerializedName("spark.streaming.unpersist")
    private boolean streamingUnpersist = true;

    //If true, Spark shuts down the StreamingContext gracefully on JVM shutdown rather than immediately.
    @SerializedName("spark.streaming.stopGracefullyOnShutdown")
    private boolean streamingStopGracefullyOnShutdown = false;

    //Maximum rate (number of records per second) at which data will be read from each Kafka partition when using the
    // new Kafka direct stream API. See the Kafka Integration guide for more details.
    @SerializedName("spark.streaming.kafka.maxRatePerPartition")
    private String streamingKafkaMaxRatePerPartition = "not set";

    //Maximum number of consecutive retries the driver will make in order to find the latest offsets on the leader of
    // each partition (a default value of 1 means that the driver will make a maximum of 2 attempts). Only applies to
    // the new Kafka direct stream API.
    @SerializedName("spark.streaming.kafka.maxRetries")
    private long streamingKafkaMaxRetries = 1;

    //How many batches the Spark Streaming UI and status APIs remember before garbage collecting.
    @SerializedName("spark.streaming.ui.retainedBatches")
    private long streamingUiRetainedBatches = 1000;

    //Whether to close the file after writing a write ahead log record on the driver. Set this to 'true' when you
    // want to use S3 (or any file system that does not support flushing) for the metadata WAL on the driver.
    @SerializedName("spark.streaming.driver.writeAheadLog.closeFileAfterWrite")
    private boolean streamingDriverWriteAheadLogCloseFileAfterWrite = false;

    //Whether to close the file after writing a write ahead log record on the receivers. Set this to 'true' when you
    // want to use S3 (or any file system that does not support flushing) for the data WAL on the receivers.
    @SerializedName("spark.streaming.receiver.writeAheadLog.closeFileAfterWrite")
    private boolean streamingReceiverWriteAheadLogCloseFileAfterWrite = false;

    //Number of threads used by RBackend to handle RPC calls from SparkR package.
    @SerializedName("spark.r.numRBackendThreads")
    private long rNumRBackendThreads = 2;

    //Executable for executing R scripts in cluster modes for both driver and workers.
    @SerializedName("spark.r.command")
    private String rCommand = "Rscript";

    //Executable for executing R scripts in client modes for driver. Ignored in cluster modes.
    @SerializedName("spark.r.driver.command")
    private String rDriverCommand = "spark.r.command";

    //Executable for executing sparkR shell in client modes for driver. Ignored in cluster modes. It is the same as
    // environment variable SPARKR_DRIVER_R, but take precedence over it. spark.r.shell.command is used for sparkR
    // shell while spark.r.driver.command is used for stillRunning R script.
    @SerializedName("spark.r.shell.command")
    private String rShellCommand = "R";

    //Connection timeout set by R process on its connection to RBackend in seconds.
    @SerializedName("spark.r.backendConnectionTimeout")
    private long rBackendConnectionTimeout = 6000;

    //Interval for heartbeats sent from SparkR backend to R process to prevent connection timeout.
    @SerializedName("spark.r.heartBeatInterval")
    private long rHeartBeatInterval = 100;

    //Checkpoint interval for graph and message in Pregel. It used to avoid stackOverflowError due to long lineage
    // chains after lots of iterations. The checkpoint is disabled by default.
    @SerializedName("spark.graphx.pregel.checkpointInterval")
    private long graphxPregelCheckpointInterval = -1;

    //The recovery mode setting to recover submitted Spark jobs with cluster mode when it failed and relaunches. This
    // is only applicable for cluster mode when stillRunning with Standalone or Mesos.
    @SerializedName("spark.deploy.recoveryMode")
    private String deployRecoveryMode;

    //When `spark.deploy.recoveryMode` is set to ZOOKEEPER, this configuration is used to set the zookeeper URL to
    // connect to.
    @SerializedName("spark.deploy.zookeeper.url")
    private String deployZookeeperUrl;

    //When `spark.deploy.recoveryMode` is set to ZOOKEEPER, this configuration is used to set the zookeeper directory
    // to store recovery state.
    @SerializedName("spark.deploy.zookeeper.dir")
    private String deployZookeeperDir;
}