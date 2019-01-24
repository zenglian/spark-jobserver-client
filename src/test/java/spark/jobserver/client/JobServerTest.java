/*
 * Copyright
 *
 * @author      Leon Zeng
 * @version     1.0
 * Created       2018/4/29 0:42
 */
package spark.jobserver.client;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;

import junit.framework.TestCase;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 */
public class JobServerTest extends TestCase {
  private static JobServerService service = JobServerService.newInstance("http://s2:8090");

  public void test() {
    HashMap a = new HashMap() {{
      put("a", "b");
      put("c", "d");
    }};
    System.out.println(a.keySet().toString());
  }

  public void testGets() throws IOException {
    List<JobInfo> jobs = service.getJobs().execute().body();
    System.out.println(jobs);

    JobInfo job = service.getJobInfo("384cb740-6fc1-44d3-94c3-4b2e2067846b").execute().body();
    System.out.println(job);
    String config =
        service.getJobConfig("384cb740-6fc1-44d3-94c3-4b2e2067846b").execute().body().string();

    HashMap<String, Binary> binaries = service.getBinaries().execute().body();
    System.out.println(binaries);

    List<String> contexts = service.getContexts().execute().body();
    System.out.println(contexts);
  }

  public void testUploadJar() throws IOException, InterruptedException {
    String fileName = "d:/tmp/s2.jar";
    String appName = "query-status2";
    System.out.println("---" + service.uploadJar(appName, fileName));
  }

  public void testUploadJarAsync() throws IOException, InterruptedException {
    String fileName = "D:/scratch/llian/workspaces/spark/jars/spark-app.jar";
    String appName = "m2";
    RequestBody file = RequestBody.create(MediaType.parse("application/java-archive"),
        new File(fileName));
    Call<String> call = service.uploadJar(appName, file);
    CountDownLatch latch = new CountDownLatch(1);
    call.enqueue(new Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        System.out.println(response.body());
        System.out.println(response.message());
        latch.countDown();
      }

      @Override
      public void onFailure(Call<String> call, Throwable throwable) {
        throwable.printStackTrace();
      }
    });
    latch.await();
  }

  public void testContext() throws IOException {
    SparkConf conf = new SparkConf().contextFactory("spark.jobserver.context" +
        ".StreamingContextFactory");
    String ok =
        service.createContext("query-status2" + System.currentTimeMillis(), conf.toMap()).execute().errorBody().string();
    System.err.println(ok);
  }

  public void testDeleteQueryStatus2() throws IOException {
    String appName = "query-status2";
    String contextName = appName + "-context";
    Result result = service.deleteContext(contextName).execute().body();
    System.out.println(result);
  }

  public void testQueryStatus2() throws IOException {
    String fileName = "d:/tmp/s2.jar";
    String appName = "query-status2";
    String contextName = appName + "-context";
    service.uploadJar(appName, fileName);
    Result result = service.deleteContext(contextName).execute().body();
    //System.out.println(result.getResult());
    SparkConf conf1 = new SparkConf()//.contextFactory("spark.jobserver.context
        // .StreamingContextFactory")
        .jars("hdfs://hdfs-name:9000/apps/spark/*");
    result = service.createContext(contextName, conf1.toMap()).execute().body();
    Assert.assertEquals("SUCCESS", result.getStatus());
    System.out.println(result.getResult());
    SparkConf conf2 = new SparkConf().appName(appName).context(appName + "-context")
        .classPath("demo.spark.jobserver.querystatus.JobServerWrapper").sync(false);
    Object info = service.startJob(conf2.toMap()).execute().body();

    //Assert.assertNotEquals(JobStatus.ERROR, info.getStatus());
  }

  public void testDeletes() throws IOException {
    String x = service.deleteBinary("sql-app").execute().body();
    System.out.println(x);
    Result y = service.deleteContext("").execute().body();
    String z = service.deleteData("").execute().body();
  }
}
