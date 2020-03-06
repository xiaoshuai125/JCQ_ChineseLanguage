import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import top.xsinfo.cnlp.cshell.CodeMapping;
import top.xsinfo.cnlp.cshell.UserSession;
import top.xsinfo.cnlp.cshell.element.Element;
import top.xsinfo.cnlp.cshell.element.ElementCreateException;
import top.xsinfo.cnlp.cshell.element.FunctionInvokeException;

import java.util.*;

public class Test1 {
    private static Map<Long, UserSession> users = new HashMap<>();
    public static UserSession getUser(long id) {
        UserSession userSession = users.get(id);
        if (userSession == null) {
            userSession = new UserSession(id);
            users.put(id,userSession);
        }
        return userSession;

    }
    @Before
    public void before(){
        strs = new ArrayList<>();
    }

    List<String> strs;
    @After
    public void after() throws ElementCreateException, FunctionInvokeException {
        for (String str : strs) {
            Element mapping = CodeMapping.mapping(str);
            final String invoke = mapping.invoke(getUser(123456), new HashMap<>());
            System.out.println("invoke = " + invoke);
        }
    }



    @Test
    public void test1(){
        //变量不需要加引号,固定的值需要加引号

//        strs.add("吾有一术名曰师弟信息 其必有名,年龄 宣 吾宣信息乃则其名与年龄合并之也 取其信息者也");
//        strs.add("古人云执其师弟信息:\"八戒\",\"18\"乎云云");
//        strs.add("吾之师弟乃\"八戒\"也");
//        strs.add("吾之2师弟同为师弟也");
//        strs.add("古人云师弟云云");
//        strs.add("古人云自\"65\"增之云云");
//        strs.add("吾有一图名曰信息 宣 名:\"孙悟空\",年龄:\"500\" 也");
//        strs.add("令吾图信息取之\"年龄\"也");
//        strs.add("令吾图信息增以 师弟:师弟,三师弟:\"沙僧\" 也");
//        strs.add("令吾图信息去以\"三师弟\"也");
//        strs.add("令吾图信息取之\"三师弟\"也");
//        strs.add("计次 \"5\" 宣 古人云\"666啊*5次\"云云  则以");
//        strs.add("吾之年龄乃\"18\"也");
//        strs.add("计次 年龄 宣 古人云\"666啊*年龄次\"云云  则以");
//        strs.add("吾之二师兄乃\"八戒\"也");
//        strs.add("维持 二师兄 宣 古人云\"正在使用while循环\"云云;吾之二师兄乃\"null\"也 则以");
//        strs.add("吾有一术名曰 取师弟 宣 持 师弟 有之 吾宣师弟乃\"八戒和沙僧\"也 则以 取其师弟者也");
//        strs.add("吾有一术名曰 取师弟 宣 不持 师弟 有之 吾宣师弟乃\"八戒和沙僧\"也无之吾宣师弟乃\"吾沙僧\"也 则以 取其师弟者也");
//        strs.add("古人云执其 取师弟 乎云云");

//
//        strs.add("吾有一术名曰 取小弟 宣 " +
//                "吾有一图名曰小图 宣 名:\"孙悟空\",年龄:\"500\" 也;" +
//                "令吾图小图增以 师弟:师弟,三师弟:\"沙僧\" 也;" +
//                "吾宣小弟乃 令吾图小图取之\"三师弟\"也 也;" +
//                "取其小弟者也");
//
//        strs.add("执其取小弟乎");//invoke = 沙僧

        strs.add("吾之今年年龄乃\"18\"也");

        strs.add("吾有一术名曰 明年多大 其必有 年龄 宣 " +
                "吾宣明年乃自年龄增之也;" +
                "吾宣明年信息乃则其\"明年我\"与明年合并之也;" +
                "取其明年信息者也");

        strs.add("");
        //invoke = 明年我19



    }



}
