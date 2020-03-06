package top.xsinfo.cnlp.cshell.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 控制台日志输出类
 * 调试时候使用的,没啥卵用
 *
 * **/
public class ConsoleLog implements Log {
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

    @Override
    public void logE(String title, String message) {
        System.out.println(sdf.format(new Date())+"错误"+title+" = " + message);
    }
    @Override
    public void logI(String title, String message) {
        System.out.println(sdf.format(new Date())+"信息"+title+" = " + message);
    }

}
