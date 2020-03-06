package top.xsinfo.cnlp.cshell;

import top.xsinfo.cnlp.cshell.common.Log;
import top.xsinfo.cnlp.cshell.element.Element;
import top.xsinfo.cnlp.cshell.element.ElementCreateException;
import top.xsinfo.cnlp.cshell.element.FunctionInvokeException;

import java.util.HashMap;
import java.util.Map;

/**
 * 核心控制类
 */
public class Core {
    private static Map<Long, UserSession> users = new HashMap<>();
    public static UserSession getUser(long id) {
        UserSession userSession = users.get(id);
        if (userSession == null) {
            log.logI("用户"+id+"信息","新用户.正在创建UserSession...");
            userSession = new UserSession(id);
            users.put(id,userSession);
        }
        log.logI("用户"+id+"信息","获取UserSession成功");
        return userSession;

    }
    private static Log log;
    public static void setLog(Log log) {
        Core.log = log;
    }

    public static Log getLog() {
        return log;
    }


    /** 处理会话 **/
    public static void mappingMsg(long id,String msg,MessageSend handler){
        Element mapping = CodeMapping.mapping(msg);
        try {
            final String invoke = mapping.invoke(getUser(id), new HashMap<>());
            if ("null".equals(invoke)){
                handler.SendMessage(id,"程序执行完毕");
            }else{
                handler.SendMessage(id,"程序执行完毕返回结果为:"+invoke);
            }
        } catch (ElementCreateException | FunctionInvokeException e) {
            log.logI("用户:"+id+"程序错误",e.getMessage());
            handler.SendMessage(id,"程序执行错误:"+e.getMessage());
            e.printStackTrace();
        }
    }

}
