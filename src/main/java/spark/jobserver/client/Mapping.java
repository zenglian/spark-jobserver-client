/*
 * Copyright
 *
 * @author      Leon Zeng
 * @version     1.0
 * Created       2018/5/3 20:25
 */

package spark.jobserver.client;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public interface Mapping {
    /**
     * Convert bean to Map.
     * @return Map of fields.
     */
    default Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                Object value = field.get(this);
                if (value == null) continue;
                //if ((value instanceof Number) && ((Number) value).doubleValue() < 0) continue;
                if(field.isAnnotationPresent(SerializedName.class))
                    result.put((field.getAnnotation(SerializedName.class)).value(), value);
                else
                    result.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage() + ": " + field.getName());
            }
        }
        return result;
    }
}
