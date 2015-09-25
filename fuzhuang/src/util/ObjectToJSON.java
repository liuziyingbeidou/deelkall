
package util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 类描述：java对象转换成json
 * @author liuzy
 * @version 1.0
 */
public class ObjectToJSON {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private JsonGenerator jsonGenerator = null;
    private Object bean = null;
	
    public void ObjecyToJSON(){
    	init();
    }
    
    public void init() {
        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void destory() {
        try {
            if (jsonGenerator != null) {
                jsonGenerator.flush();
            }
            if (!jsonGenerator.isClosed()) {
                jsonGenerator.close();
            }
            jsonGenerator = null;
            objectMapper = null;
            bean = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * 描述：JavaBean(Entity/Model)转换成JSON
	 * <b>function:</b>将java对象转换成json字符串
	 * @author liuzy
	 * @createDate 2015-03-27 下午22:32:10
	 */
	public static void writeEntityJSON(Object bean) {
	    
	    try {
//	        System.out.println("jsonGenerator");
//	        //writeObject可以转换java对象，eg:JavaBean/Map/List/Array等
//	        jsonGenerator.writeObject(bean);    
//	        System.out.println();
//	        
//	        System.out.println("ObjectMapper");
	        //writeValue具有和writeObject相同的功能
	        objectMapper.writeValue(System.out, bean);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	

	 /**
	  * 描述：将Map集合转换成Json字符串
	  * <b>function:</b>将map转换成json字符串
	  * @author liuzy
	  * @createDate 2015-03-27 下午22:42:26
	  */
	 public void writeMapJSON() {
	     try {
	         Map<String, Object> map = new HashMap<String, Object>();
	         bean = new Object();
	         map.put("account", bean);
	         
	         System.out.println("jsonGenerator");
	         jsonGenerator.writeObject(map);
	         System.out.println("");
	         
	         System.out.println("objectMapper");
	         objectMapper.writeValue(System.out, map);
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	 }
	
	 /**
	  * 描述：将List集合转换成json
	  * <b>function:</b>将list集合转换成json字符串
	  * @author liuzy
	  * @createDate 2015-03-27 下午22:43:59
	  */
	 public static String writeListJSON(List<?> list) {
		 String json = null;
	     try {
	         //list转换成JSON字符串
//	    	 JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
//	         jsonGenerator.writeObject(list);
	         //用objectMapper直接返回list转换成的JSON字符串
	    	
	         json = objectMapper.writeValueAsString(list);
	         //objectMapper list转换成JSON字符串
//	         objectMapper.writeValue(System.out, list);
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	     return json;
	 }
	 
	 
	
}
