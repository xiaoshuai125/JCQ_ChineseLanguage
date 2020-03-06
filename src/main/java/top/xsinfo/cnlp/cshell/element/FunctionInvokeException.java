package top.xsinfo.cnlp.cshell.element;

/**
 * 函数执行异常
 * 变量没有声明等
 */
public class FunctionInvokeException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public FunctionInvokeException(String message) {
        super(message);
    }
}
