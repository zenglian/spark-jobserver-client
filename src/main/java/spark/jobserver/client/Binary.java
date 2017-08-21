package spark.jobserver.client;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

import lombok.*;

@Getter @Setter
public class Binary{
	@SerializedName("binary-type")  
	private String binary_type;
	@SerializedName("upload-time")  
	private Date upload_time;
}
