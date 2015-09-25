package exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

@SuppressWarnings("serial")
public class ErrorInterceptor implements Interceptor {
	
	//@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		HttpServletRequest request = (HttpServletRequest) arg0.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		//return "ex";
		try {

			return arg0.invoke();

	       } 
	       catch (TxException ex) {
	    	   return "msg";
	       }catch (Exception ex) {
	    	   StackTraceElement[] stack =  ex.getStackTrace();
	    	   StringBuffer bf = new StringBuffer();
	    	   for(StackTraceElement e:stack){
	    		   bf.append(e.getClassName() + "#" +e.getLineNumber() + "#"+  e.getMethodName()+"<BR>");
	    	   }
	    	   request.setAttribute("MSG", bf.toString());
	    	   return "msg";
	       }
		
	}
	
	
	//@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}





	

}
