package spark.jobserver.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.*;

@Getter @Setter
public class Pojo {
	public final static transient Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Override
	public String toString(){
		return gson.toJson(this);
	}
}
