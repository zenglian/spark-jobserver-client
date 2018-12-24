/*
 * Copyright
 *
 * @author      Leon Zeng
 * @version     1.0
 * Created       2018/4/28 22:23
 */

package spark.jobserver.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Java client implements Rest APIs provided by <a href="https://github.com/ooyala/spark-jobserver">Spark
 * Job Server</a>. For more information, refer to <a href="https://github.com/ooyala/spark-jobserver">
 * https://github.com/ooyala/spark-jobserver</a>.
 */
public interface JobServerService {
    @GET("binaries")
    Call<HashMap<String, Binary>> getBinaries();

    @POST("binaries/{appName}")
    Call<String> uploadJar(@Path("appName") String appName, @Body RequestBody file);

    @DELETE("binaries/{appName}")
    Call<String> deleteBinary(@Path("appName") String appName);

    @GET("contexts")
    Call<List<String>> getContexts();

    @GET("contexts/{name}")
    Call<String> getContext(@Path("name") String name);

    @POST("contexts/{name}")
    Call<Result> createContext(@Path("name") String name, @QueryMap Map<String, Object> params);

    @DELETE("contexts/{name}")
    Call<Result> deleteContext(@Path("name") String name);

    // shuts down all contexts and re-loads only the contexts from config, use sync=false to execute asynchronously.
    @PUT("contexts?reset=reboot")
    Call<String> resetAllContexts();

    @GET("jobs")
    Call<List<JobInfo>> getJobs();

    @GET("jobs/{jobId}")
    Call<JobInfo> getJobInfo(@Path("jobId") String jobId);

    /**
     * Get job config.
     *
     * @param jobId
     * @return See https://github.com/lightbend/config.
     */
    @GET("jobs/{jobId}/config")
    Call<ResponseBody> getConfig(@Path("jobId") String jobId);

    @POST("jobs")
    Call<JobInfo> startJob(@QueryMap Map<String, Object> params);

    @DELETE("jobs/{jobId}")
    Call<String> killJob(@Path("jobId") String jobId);

    @GET("data")
    Call<List<String>> getData();

    //Uploads a new file, the full path of the file on the server is returned, the
    //prefix is the prefix of the actual filename used on the server (a timestamp is
    //added to ensure uniqueness)
    @POST("data/{prefix}")
    Call<DataResult> uploadData(@Path("prefix") String prefix, @Body RequestBody file);

    // Deletes the specified file (only if under control of the JobServer)
    @DELETE("data/{filename}")
    Call<String> deleteData(@Path("filename") String filename);

    // Deletes all uploaded files. Use ?sync=false to execute asynchronously.
    @PUT("data?reset=reboot")
    Call<String> resetData();

    /* Factory method */
    static JobServerService newInstance(String url) {
        Gson gson = new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor((msg)-> {
                System.out.println("- " + msg);
        });
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logger)
                .readTimeout(3, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES)
                .build();
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)).build()
                .create(JobServerService.class);
    }

    default String uploadJar(String appName, String path) throws IOException {
        RequestBody file = RequestBody.create(MediaType.parse("application/java-archive"), new File(path));
        return this.uploadJar(appName, file).execute().body();
    }
}
