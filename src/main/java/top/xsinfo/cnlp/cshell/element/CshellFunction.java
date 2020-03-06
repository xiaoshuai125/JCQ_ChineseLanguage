package top.xsinfo.cnlp.cshell.element;

import top.xsinfo.cnlp.cshell.UserSession;

import java.util.List;
import java.util.Map;
/** 函数模板 **/
public abstract class CshellFunction {
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
    public abstract String invoke(Element element, Map<String,String> data, UserSession user, List<String>args) throws ElementCreateException, FunctionInvokeException;

    /**
     * 保存函数到用户全局变量表
     * @param name 函数名
     * @param user  用户会话
     * @param element 函数体
     */
    public void save(String name,UserSession user,Element element){
        user.setProperties(name,element.toJson());
    }
}
