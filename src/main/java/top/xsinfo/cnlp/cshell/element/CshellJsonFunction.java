package top.xsinfo.cnlp.cshell.element;

import top.xsinfo.cnlp.cshell.UserSession;

import java.util.List;
import java.util.Map;

/**
 * 自定义函数
 */
public class CshellJsonFunction extends CshellFunction {

    @Override
    public String invoke(Element element, Map<String, String> data, UserSession user,List<String>args) throws ElementCreateException, FunctionInvokeException {
        final List<String> args1 = element.getArgs();
        if(args1!=null&&args!=null){
            for (int i = 0; i < args1.size(); i++) {
                String s = args.get(i).trim() ;
                try{
                    while (true){
                        s = Element.createElement(s).invoke(user,data);
                    }
                }catch (ElementCreateException e){}
                data.put(args1.get(i).trim(),s);
            }
        }

        List<String> funs = element.getFuns();
        for (String fun : funs) {
            final Element element1 = Element.createElement(fun);
            element1.invoke(user,data);
        }

        String ret = element.getRet();
        if (!"null".equals(ret)){
            return element.getValue(data,user,ret);
        }
        return "null";
    }

}
