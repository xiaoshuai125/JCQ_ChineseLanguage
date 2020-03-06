package top.xsinfo.cnlp.cshell.element;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringEscapeUtils;
import top.xsinfo.cnlp.cshell.Core;
import top.xsinfo.cnlp.cshell.Keywords;
import top.xsinfo.cnlp.cshell.UserSession;
import top.xsinfo.cnlp.cshell.common.Log;
import top.xsinfo.cnlp.cshell.common.StringTool;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 对象类
 * 所有格式皆为对象
 * 判断对象方法是判断type值
 */
public class Element  {
    public Element() {}

    public Element(String name, String type, List<String> args, List<String> funs, String ret) {
        this.name = name;
        this.type = type;
        this.args = args;
        this.funs = funs;
        this.ret = ret;
    }

    private static Log log = Core.getLog();
    //解析json用到的工具类
    private static ObjectMapper mapper = new ObjectMapper();



    /** 名称 **/
    private String name;
    /**
     * 类型
     *
     * 返回keywords里面的第一个
     *
     * 如果是函数则返回 吾有一术名曰
     *
     * 如果返回的是Code
     * 那么代表这里是代码语句
     *
     */
    private String type;
    /** 参数 **/
    private List<String> args;
     /** 函数体1
      *
      *
     * if、for、while、function
      * 按照分号分割
     * **/
     private List<String> funs;
    /** 返回值  只有函数和常量用到 **/
    private String ret;



    /**执行当前 Element **/
    public String invoke(UserSession userSession,Map<String,String>data) throws ElementCreateException, FunctionInvokeException {
        String v_ret = "null";

        switch (type){

            case Keywords.KEY_var :

                //---------------------声明全局变量------------------------------------------

                Element e = createElement(funs.get(0));
                if (e != null) {
                    userSession.setProperties(name,e.invoke(userSession,data));
                }else{
                    throw new ElementCreateException("创建Element全局变量失败:" + funs.get(0));
                }
                return v_ret;
            case Keywords.KEY_mvar :

                //---------------------声明局部变量------------------------------------------

                Element e2 = createElement(funs.get(0));
                if (e2 != null) {
                    data.put(name,e2.invoke(userSession,data));
                }else{
                    throw new ElementCreateException("创建Element局部变量失败:" + funs.get(0));
                }
                return v_ret;
            case Keywords.KEY_math_begin :

                //---------------------数字自增和自减------------------------------------------
                int a;
                String invoke = null;
                try {
                    invoke = createElement(funs.get(0)).invoke(userSession, data);
                    a = Integer.parseInt(invoke);
                }catch (NumberFormatException ne){
                    throw  new ElementCreateException("数字变量自增失败:num:"+invoke);
                }

                if(args.get(0).equals(Keywords.KEY_math_jia)){
                    return String.valueOf(++a);
                }else if(args.get(0).equals(Keywords.KEY_math_jian)){
                    return String.valueOf(--a);
                }
            case Keywords.KEY_function :
                //---------------------声明函数------------------------------------------
                CshellFunction createFun =  new CshellJsonFunction();
                createFun.save(name,userSession,this);
                return v_ret;
            case Keywords.KEY_data :

                //---------------------声明Map对象------------------------------------------

                MapBean mb1 = new MapBean(name,userSession,this,data);
                mb1.save();
                return v_ret;
            case Keywords.KEY_data_edit :

                //---------------------对Map对象进行编辑------------------------------------------

                MapBean mb = new MapBean(name,userSession,this,data);
                if(args.get(0).equals(Keywords.KEY_data_add_get)){
                    return mb.get(funs.get(0),userSession,data);
                }else
                if(args.get(0).equals(Keywords.KEY_data_add_new)){
                    mb.add(funs.get(0));
                }else
                if(args.get(0).equals(Keywords.KEY_data_remove_new)){
                    mb.remove(funs.get(0),userSession,data);
                }
                return v_ret;
            case Keywords.KEY_while :

                //---------------------while循环------------------------------------------


                whileBean(this,userSession,data);
                return v_ret;
            case Keywords.KEY_for :
                //---------------------for循环------------------------------------------

                forBean(this,Element.createElement(args.get(0)).invoke(userSession,data),userSession,data);
                return v_ret;
            case Keywords.KEY_if :
                //---------------------if判断------------------------------------------

                final String if_arg = createElement(args.get(0)).invoke(userSession,data);
                boolean bool = Keywords.KEY_if_not.equals(args.get(1));
                boolean equals = "null".equals(if_arg);
                if (bool) equals = !equals;

                if (equals){
                    //不成立
                    for (int i= 2;i<args.size();i++) {
                        createElement(args.get(i)).invoke(userSession,data);
                    }
                    return v_ret;
                }
                for (String fun : funs) {
                    if("null".equals(fun))continue;
                    createElement(fun).invoke(userSession,data);
                }
                return v_ret;
            case Keywords.KEY_println :

                //---------------------控制台打印------------------------------------------
                CshellFunction c2 = new ConsoleFunction();
                c2.invoke(createElement(funs.get(0)),data,userSession,null);
                break;
            case Keywords.KEY_String_start:

                //---------------------字符串相加------------------------------------------

                Element e11 = Element.createElement(funs.get(0));
                Element e12 = Element.createElement(funs.get(1));
                final String s1 = e11.invoke(userSession, data);
                final String s2 = e12.invoke(userSession, data);
                return s1+s2 ;

            case Keywords.KEY_function_run:
                //-----------------------执行函数--------------
                final String value2 = getValue(data, userSession, name);
                final Element funElement = createElement(value2);
                CshellFunction cf = new CshellJsonFunction();
                return cf.invoke(funElement,data,userSession,args);
            case "key":

                //---------------------变量取值------------------------------------------

                final String value = getValue(data, userSession, name);
                String invoke1;
                try{
                    Element element = createElement(value);
                    if (element==null){
                        throw new FunctionInvokeException("变量:\""+name+"\"没有声明");
                    }
                    invoke1= element.invoke(userSession, data);
                    while(true){
                        invoke1 = Element.createElement(invoke1).invoke(userSession, data);
                    }

                }catch (ElementCreateException exc){
                    final String value1 = getValue(data, userSession, name);
                    return StringTool.remove_yin_hao(value1);
                }

            case "value":

                //---------------------常量值------------------------------------------

                return StringTool.remove_yin_hao(ret);
        }


        return v_ret;
    }

    public static Element getValueElement(String value){
        return new Element(null,"value",null,null,value);
    }

    public static Element getkeyElement(String value){
        return new Element(value,"key",null,null,null);
    }



    /** 获取变量的值 **/
    public String getValue(Map<String,String>data,UserSession userSession,String key){
        String str = data.get(key);
        if (str == null) {
            str = userSession.getProperties(key);
        }

        return str;
    }

    /** 设置变量的值 **/
    public void setValue(Map<String,String>data,UserSession userSession,String key,String value){
        if(data.get(key)==null){
            userSession.setProperties(key,value);
        }else{
            data.put(key,value);
        }
    }


    /** 将json调用为一个Element **/
    public String toJson() {
        //将返回的json通过解析解析成一个对象
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 将json调用为一个Element **/
    public static Element createElement(String json) throws ElementCreateException {
        //将返回的json通过解析解析成一个对象
        try {
            return mapper.readValue(json, Element.class);
        } catch (IOException e) {
            throw new ElementCreateException("Element创建失败:"+e.getMessage());
        }

    }

    //-------------------------结构函数---------------
    /** for语句的执行过程 **/
    public void forBean(Element e, String times, UserSession userSession,Map<String,String>data) throws ElementCreateException, FunctionInvokeException {
        final List<String> funs = e.getFuns();
        for (int i = 0; i < Integer.parseInt(times.trim()); i++) {
            for (String fun : funs) {
                Element.createElement(fun).invoke(userSession,data);
            }
        }
    }
    /** while语句的执行过程 **/
    public void whileBean(Element e, UserSession user,Map<String,String> data) throws ElementCreateException, FunctionInvokeException {
        String p;
        try{
            p = Element.createElement(e.getArgs().get(0)).invoke(user,data);
        }catch (FunctionInvokeException while_e){
            p = "null";
        }
        final List<String> funs = e.getFuns();
        while ((!"null".equals(p.trim()))&&(!"0".equals(p.trim()))){
            for (String fun : funs) {
                Element.createElement(fun).invoke(user,data);
                try{
                    p = Element.createElement(e.getArgs().get(0)).invoke(user,data);
                }catch (FunctionInvokeException while_e){
                    p = "null";
                }
            }
        }
    }

    //----------------------------------------object方法---------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public List<String> getFuns() {
        return funs;
    }

    public void setFuns(List<String> funs) {
        this.funs = funs;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    @Override
    public String toString() {
        return "Element{" +
                "log=" + log +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", args=" + args +
                ", funs=" + funs +
                ", ret='" + ret + '\'' +
                '}';
    }
}
