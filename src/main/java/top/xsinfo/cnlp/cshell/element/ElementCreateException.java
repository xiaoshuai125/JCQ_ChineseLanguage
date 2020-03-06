package top.xsinfo.cnlp.cshell.element;

/**
 * 如果不是一个ELement对象会抛出此异常
 */
public class ElementCreateException extends Exception {

    public ElementCreateException(String message) {
        super(message);
    }
}
