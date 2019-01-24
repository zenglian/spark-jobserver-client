package spark.jobserver.client;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

@Data
public class Binary {
  @SerializedName("binary-type")
  private String binaryType;
  @SerializedName("upload-time")
  private Date uploadTime;
}
