package exception;

@SuppressWarnings("serial")
public class JException extends RuntimeException { 
	  
    /** 
     * 构建新的运行时异常 
     * @since 0.1 
     */ 
    public JException() { 
        super(); 
    } 
 
    /** 
     * 用指定的详细消息构建新的运行时异常 
     * @param msg 详细消息 
     * @since 0.1 
     */ 
    public JException(String msg) { 
        super(msg); 
    } 
 
    /** 
     * 用指定的详细消息和原因构造一个新的运行时异常 
     * @param msg 详细消息 
     * @param cause 原因 
     * @since 0.1 
     */ 
    public JException(String msg, Throwable cause) { 
        super(msg, cause); 
    } 
 
    /** 
     * 用指定的原因和详细消息构造一个新的运行时异常 
     * @param cause 原因 
     * @since 0.1 
     */ 
    public JException(Throwable cause) { 
        super(cause); 
    } 
} 