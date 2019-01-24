package spark.jobserver.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Java client implements Rest APIs provided by
 * <a href="https://github.com/spark-jobserver/spark-jobserver">Spark
 * Job Server</a>.
 */
@SuppressWarnings({"unused"})
public interface JobServerService {
  @GET("binaries")
  Call<HashMap<String, Binary>> getBinaries();

  @POST("binaries/{appName}")
  Call<String> uploadJar(@Path("appName") String appName, @Body RequestBody file);

  /**
   * Upload a jar file.
   * @param appName App name.
   * @param path Jar file path.
   * @return Result.
   * @throws IOException Io exception.
   */
  default String uploadJar(String appName, String path) throws IOException {
    RequestBody file = RequestBody.create(MediaType.parse("application/java-archive"),
        new File(path));
    return this.uploadJar(appName, file).execute().body();
  }

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

  /**
   * shuts down all contexts and re-loads only the contexts from config, use sync=false to
   * execute asynchronously.
   */
  @PUT("contexts?reset=reboot")
  Call<String> resetAllContexts();

  @GET("jobs")
  Call<List<JobInfo>> getJobs();

  @GET("jobs/{jobId}")
  Call<JobInfo> getJobInfo(@Path("jobId") String jobId);

  @GET("jobs/{jobId}/config")
  Call<ResponseBody> getJobConfig(@Path("jobId") String jobId);

  @POST("jobs")
  Call<JobInfo> startJob(@QueryMap Map<String, Object> params);

  @DELETE("jobs/{jobId}")
  Call<String> killJob(@Path("jobId") String jobId);

  @GET("data")
  Call<List<String>> getData();

  /**
   * Uploads a new file, the full path of the file on the server is returned. The
   * prefix is the prefix of the actual filename used on the server (a timestamp is
   * added to ensure uniqueness)
   */
  @POST("data/{prefix}")
  Call<DataResult> uploadData(@Path("prefix") String prefix, @Body RequestBody file);

  @DELETE("data/{filename}")
  Call<String> deleteData(@Path("filename") String filename);

  /**
   * Deletes all uploaded files. Use ?sync=false to execute asynchronously.
   *
   * @return Result.
   */
  @PUT("data?reset=reboot")
  Call<String> resetData();

  /**
   * create an instance.
   */
  static JobServerService newInstance(String url) {
    Gson gson = new GsonBuilder().setLenient().create();
    HttpLoggingInterceptor logger = new HttpLoggingInterceptor(x -> System.out.println("- " + x));
    logger.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logger)
        .readTimeout(3, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES).build();
    return new Retrofit.Builder()
        .baseUrl(url)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson)).build()
        .create(JobServerService.class);
  }
}
