package top.xsinfo.cnlp.cshell.common;
/** 日志输出 **/
public interface Log {
    /** 输出错误信息 **/
    void logE(String title,String message);
    /** 输出信息 **/
    void logI(String title,String message);
}
