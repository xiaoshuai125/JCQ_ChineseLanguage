package top.xsinfo.cnlp.cshell.element;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.xsinfo.cnlp.cshell.CodeMapping;
import top.xsinfo.cnlp.cshell.Keywords;
import top.xsinfo.cnlp.cshell.UserSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Map处理类
 * 处理Map数据类型相关的操作
 *
 */
public class MapBean {
    private static ObjectMapper mapper = new ObjectMapper();
    private String name;
    private Map<String,String> data;
    private Map<String,String> myMap;
    private UserSession user;
    private Element element;



    /**
     * 创建一个map对象
     * @param name 函数名
     * @param user  用户会话
     * @param element 函数体
     */
    public MapBean(String name, UserSession user, Element element,Map<String,String>data) {
        this.name = name;
        this.user = user;
        this.data = data;
        this.element = element;
        myMap = jsonToMap(element.getValue(data,user,name));
    }

    /**保存到用户全局变量表*/
    public void save(){
        user.setProperties(name.trim(),element.getFuns().get(0));
    }


    /** 对map对象进行增加操作 **/
    public void add(String str){
        final Map<String, String> map = jsonToMap(str);
        for (String s : map.keySet()) {
            myMap.put(s,map.get(s));
        }
        updata(name,myMap);
    }

    /** 删除 **/
    public void remove(String str,UserSession userSession,Map<String,String>data) throws ElementCreateException, FunctionInvokeException {
        final Element element = Element.createElement(str);
        final String invoke = element.invoke(userSession, data).trim();
        myMap.remove(invoke);
        updata(name,myMap);
    }

    /** 获取元素 **/
    public String get(String str,UserSession userSession,Map<String,String>data) throws ElementCreateException, FunctionInvokeException {
        final Element element = Element.createElement(str);
        final String invoke = element.invoke(userSession, data);
        final String s = myMap.get(invoke);
        if (s == null) {
            return "null";
        }
        final Element e_val = Element.createElement(s);
        final String e_invoke = e_val.invoke(userSession, data);
        return e_invoke;
    }


    /**
     * 将普通的句子转成Map对象
     *
     * 师弟:师弟,三师弟:"沙僧"
     * **/
    public static Map<String,String> strToMap(String str){
        Map<String,String> map1 = new HashMap<>();
        String[] strs = str.trim().split(",");
        for (String s : strs) {
            final String[] split = s.split(":");

            if (split[1].startsWith("\"")&&split[1].endsWith("\"")) {
                map1.put(split[0].trim(),Element.getValueElement(split[1].trim()).toJson());
            }else{
                map1.put(split[0].trim(),Element.getkeyElement(split[1].trim()).toJson());
            }
        }


        return  map1;
    }

    /** 变量更新 **/
    public void updata(String key,Map<String,String> value){
        element.setValue(data,user,key.trim(),mapToJson(value));
    }

    /** Map转成Json **/
    public static String mapToJson(Map<String,String>map){
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /** Json转成Map **/
    public static Map<String,String> jsonToMap(String json){
        try {
            return mapper.readValue(json,Map.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
