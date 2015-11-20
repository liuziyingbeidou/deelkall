package util;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @ClassName: HttpTools 
 * @Description: java HttpClient URL
 * @author liuzy
 * @date 2015-10-21 下午05:00:11
 */
public class HttpTools
{

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static String getDataByURL(String url)
	{
		//url = "http://www.bavlo.com/getGemCalibrated?typeId=3&shapeId=4";
		String res = null;
		// 创建HttpClient实例   
	    HttpClient httpclient = new DefaultHttpClient();
	    // 创建Get方法实例   
        HttpGet httpgets = new HttpGet(url);  
        HttpResponse response;
        try {
			response = httpclient.execute(httpgets);
	        HttpEntity entity = response.getEntity();  
	        if (entity != null) {  
	            InputStream instreams = entity.getContent();  
	            String str = convertStreamToString(instreams);
	            res = str;
	            httpgets.abort();  
	        }
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return res;
	}
	
	public static String convertStreamToString(InputStream is) {    
		StringBuilder sb1 = new StringBuilder();      
        byte[] bytes = new byte[4096];    
        int size = 0;    
          
        try {      
            while ((size = is.read(bytes)) > 0) {    
                String str = new String(bytes, 0, size, "UTF-8");    
                sb1.append(str);    
            }    
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
            try {      
                is.close();      
            } catch (IOException e) {      
               e.printStackTrace();      
            }      
        }      
        return sb1.toString();       
    }
	
}