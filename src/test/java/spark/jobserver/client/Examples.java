/*
 * Copyright
 *
 * @author      Leon Zeng
 * @version     1.0
 * Created       2018/4/29 0:42
 */

package spark.jobserver.client;

import junit.framework.TestCase;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class Examples{ // extends TestCase {
    private static JobServerService service = JobServerService.newInstance("http://master:8090");

    public void testGets() throws IOException {
        List<JobInfo> jobs = service.getJobs().execute().body();
        System.out.println(jobs);

        JobInfo job = service.getJobInfo("384cb740-6fc1-44d3-94c3-4b2e2067846b").execute().body();
        System.out.println(job);
        String config = service.getConfig("384cb740-6fc1-44d3-94c3-4b2e2067846b").execute().body().string();

        HashMap<String, Binary> binaries = service.getBinaries().execute().body();
        System.out.println(binaries);

        List<String> contexts = service.getContexts().execute().body();
        System.out.println(contexts);
    }


    public void testUploadJar() throws IOException, InterruptedException {
        String fileName = "D:/jars/spark-app.jar";
        String appName = "m2";
        System.out.println(service.uploadJar(appName, fileName));
    }

    public void testUploadJarAsync() throws IOException, InterruptedException {
        String fileName = "D:/jars/spark-app.jar";
        String appName = "m2";
        RequestBody file = RequestBody.create(MediaType.parse("application/java-archive"), new File(fileName));
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

    public void testCreateContext() throws IOException {
        SparkConf conf = new SparkConf();
        String ok = service.createContext("context3", conf.toMap()).execute().errorBody().string();
        System.err.println(ok);
    }

    public void testStartJob() throws IOException {
        SparkConf conf = new SparkConf().appName("app1").classPath("spark.jobserver.WordCountExample").sync(true);
        service.startJob("", conf.toMap()).execute();
    }

    public void testDeletes() throws IOException {
        String x = service.deleteBinary("sql-app").execute().body();
        System.out.println(x);
        String y = service.deleteContext("").execute().body();
        String z = service.deleteData("").execute().body();
    }
}
