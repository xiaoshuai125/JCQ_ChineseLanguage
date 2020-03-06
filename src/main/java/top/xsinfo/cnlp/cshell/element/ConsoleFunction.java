package top.xsinfo.cnlp.cshell.element;

import top.xsinfo.cnlp.cshell.UserSession;

import java.util.List;
import java.util.Map;
/** 控制台输出函数类
 *
 * 你也可以参考这里来写一个自定义的函数
 * **/
public class ConsoleFunction extends CshellFunction {
    /**
     * 程序执行过程
     *
     * @param element 要执行的对象
     * @param data 数据
     * @param user 用户会话
     * @param args 参数
     * @return 返回自定义函数执行的返回值
     * @throws ElementCreateException 创建Element对象失败
     * @throws FunctionInvokeException 函数执行错误,一般为变量未初始化
     */
    @Override
    public String invoke(Element element, Map<String, String> data, UserSession user, List<String>args) throws ElementCreateException, FunctionInvokeException {
        System.out.println(element.invoke(user, data));
        return "null";
    }
}
