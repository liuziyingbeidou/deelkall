package exception;

/**
 * 
 * 
 * <p>
 * </p>
 * <p>
 * </p>
 * 
 * @author 孙朝辉
 * @version $Id: ParseCondErrException.java 52820 2014-11-11 08:47:07Z yinxianglong $
 * @since
 * 
 */

public class ParseCondErrException extends RuntimeException {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 5797702809144002358L;

    /**
     * 构建新的运行时异常
     * 
     * @since 0.1
     */
    public ParseCondErrException() {
        super();
    }

    /**
     * 用指定的详细消息构建新的运行时异常
     * 
     * @param msg
     *            详细消息
     * @since 0.1
     */
    public ParseCondErrException(String msg) {
        super(msg);
    }

    /**
     * 用指定的详细消息和原因构造一个新的运行时异常
     * 
     * @param msg
     *            详细消息
     * @param cause
     *            原因
     * @since 0.1
     */
    public ParseCondErrException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * 用指定的原因和详细消息构造一个新的运行时异常
     * 
     * @param cause
     *            原因
     * @since 0.1
     */
    public ParseCondErrException(Throwable cause) {
        super(cause);
    }
}