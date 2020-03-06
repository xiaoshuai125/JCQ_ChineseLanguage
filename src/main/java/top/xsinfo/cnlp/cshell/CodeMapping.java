package top.xsinfo.cnlp.cshell;

import top.xsinfo.cnlp.cshell.common.StringTool;
import top.xsinfo.cnlp.cshell.element.Element;
import top.xsinfo.cnlp.cshell.element.MapBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/** 代码映射工具类
 *  将文字代码映射成json代码
 * **/
public class CodeMapping {

    /** 开始逐个映射 **/
    public static Element mapping(String str){
        str = str.trim();
        List<String> args = new ArrayList<>();
        List<String> funs = new ArrayList<>();


        Element e = new Element();


        //------------------------全局变量---------------
        if ((str.startsWith(Keywords.KEY_var)||str.startsWith(Keywords.KEY_mvar))&&str.endsWith(Keywords.KEY_var_over)){
            //获取类型
            final String begin = str.startsWith(Keywords.KEY_var) ? Keywords.KEY_var : Keywords.KEY_mvar;
            final int i = str.indexOf(Keywords.KEY_var_fuzhi);
            final int i2 = str.indexOf(Keywords.KEY_var_oldToNew);
            String[] split;
            if (i>0 && i2<0 ){
                //赋值
                split = StringTool.binString(str,Keywords.KEY_var_fuzhi,0, begin,Keywords.KEY_var_over);
                args.add(Keywords.KEY_var_fuzhi);
            }else{
                //修改引用
                split = StringTool.binString(str,Keywords.KEY_var_oldToNew,0, begin,Keywords.KEY_var_over);
                args.add(Keywords.KEY_var_oldToNew);
            }

            funs.add(mapping(split[1].trim()).toJson());
            e.setFuns(funs);
            e.setName(split[0].replace(Keywords.KEY_var,"").replace(Keywords.KEY_mvar,"").trim());
            e.setArgs(args);
            e.setType(begin);
            return e;
        }
        //------------------------函数--------------
        if (str.startsWith(Keywords.KEY_function)&&str.endsWith(Keywords.KEY_function_over)){
            final String[] split = StringTool.binString(str,Keywords.KEY_function_begin,0,Keywords.KEY_function,Keywords.KEY_function_over);
            String name;

            //判断是否有参数
            if(split[0].contains(Keywords.KEY_function_pram)) {
                name = split[0].substring(0,split[0].indexOf(Keywords.KEY_function_pram));
                String pram = split[0].substring(split[0].indexOf(Keywords.KEY_function_pram)+Keywords.KEY_function_pram.length());
                args=  Arrays.asList(pram.split(","));
                StringTool.trimStrings(args);
                e.setArgs(args);
            }else{
                //无参数
                name = split[0].replace(Keywords.KEY_function,"");
            }
            e.setName(name.trim());
            String[] funss = StringTool.binString(split[1],Keywords.KEY_function_return, split[1].lastIndexOf(Keywords.KEY_function_return) - 1);
            //取函数体
            String[] functions = funss[0].split(";");
            StringTool.trimStrings(functions);
            for (String function : functions) {
                funs.add(mapping(function).toJson());
            }
            e.setFuns(funs);

            //取返回值
            e.setRet(funss[1]);
            e.setType(Keywords.KEY_function);
            return e;
        }
        //------------------------value值--------------
        if (str.startsWith("\"")&& str.endsWith("\"")){
            e.setType("value");
            //将数值设置到返回值
            e.setRet(StringTool.getCenter(str,"\"","\"").trim());
            return e;
        }
        //------------------------Map字典--------------
        if (str.startsWith(Keywords.KEY_data)&& str.endsWith(Keywords.KEY_data_over)){
            //获取类型
            final String type = Keywords.KEY_data;
            e.setType(type);
            //获取名称和字典内容
            final String[] strings = StringTool.binString(str, Keywords.KEY_data_begin, 0, type, Keywords.KEY_data_over);
            e.setName(strings[0]);
            //添加字典内容
            funs.add(MapBean.mapToJson(MapBean.strToMap(strings[1])));
            e.setFuns(funs);

            return e;
        }
        //-----------------------Map字典修改-----------------
        if (str.startsWith(Keywords.KEY_data_edit)&& str.endsWith(Keywords.KEY_data_over)){
            e.setType(Keywords.KEY_data_edit);
            final int add = str.indexOf(Keywords.KEY_data_add_new);
            final int get = str.indexOf(Keywords.KEY_data_add_get);
            final int remove = str.indexOf(Keywords.KEY_data_remove_new);

            if(add>get && add>remove){
                args.add(Keywords.KEY_data_add_new);
            }else if(get>add && get>remove){
                args.add(Keywords.KEY_data_add_get);
            }else if(remove>add && remove>get){
                args.add(Keywords.KEY_data_remove_new);
            }
            final String[] strings = StringTool.binString(str, args.get(0), 0, Keywords.KEY_data_edit, Keywords.KEY_data_over);
            if (Keywords.KEY_data_add_new.equals(args.get(0))){
                funs.add(MapBean.mapToJson(MapBean.strToMap(strings[1])));
            }else{
                funs.add(Element.getValueElement(strings[1]).toJson());
            }
            e.setArgs(args);
            e.setName(strings[0]);
            e.setFuns(funs);
            return e;
        }
        //------------------------if判断---------------
        if((str.startsWith(Keywords.KEY_if)||str.startsWith(Keywords.KEY_if_not))&&str.endsWith(Keywords.KEY_if_end)) {
            //获取类型
            final String type = str.startsWith(Keywords.KEY_if) ? Keywords.KEY_if : Keywords.KEY_if_not;
            e.setType(Keywords.KEY_if);
            //取出条件
            final String[] binString = StringTool.binString(str, Keywords.KEY_if_true, 0, type, Keywords.KEY_if_end);
            args.add(mapping(binString[0].trim()).toJson());
            args.add(type);


            //取出方法体--如果有false语句
            if (binString[1].contains(Keywords.KEY_if_false)){
                final String[] funss = StringTool.binString(binString[1], Keywords.KEY_if_false, 0);

                final String[] functionss = funss[0].split(";");
                for (String function : functionss) {
                    funs.add(mapping(function.trim()).toJson());
                }

                final String[] functions = funss[1].split(";");
                for (String function : functions) {
                    args.add(mapping(function.trim()).toJson());
                }
                e.setFuns(funs);
            }else{
                final String[] functions = binString[1].split(";");
                for (String function : functions) {
                    funs.add(mapping(function.trim()).toJson());
                }
            }
            e.setArgs(args);
            e.setFuns(funs);
            return e;
        }
        //------------------------while循环--------------
        if(str.startsWith(Keywords.KEY_while)&&str.endsWith(Keywords.KEY_while_end)) {
            //获取类型
            e.setType(Keywords.KEY_while);
            //取出条件
            final String[] binString = StringTool.binString(str, Keywords.KEY_while_begin, 0, Keywords.KEY_while, Keywords.KEY_while_end);
            args.add(mapping(binString[0]).toJson());
            e.setArgs(args);
            //取出函数体
            final String[] functions = binString[1].split(";");
            for (String function : functions) {
                funs.add(mapping(function.trim()).toJson());
            }
            e.setFuns(funs);
            return e;
        }
        //------------------------for循环--------------
        if(str.startsWith(Keywords.KEY_for)&&str.endsWith(Keywords.KEY_for_end)){
            final String[] strings = StringTool.binString(str, Keywords.KEY_for_begin, 0, Keywords.KEY_for, Keywords.KEY_for_end);
            //设置类型
            e.setType(Keywords.KEY_for);
            //设置函数体
            final String[] functions = strings[1].split(";");

            for (String function : functions) {
                funs.add(mapping(function).toJson());
            }
            //设置参数
            args.add(mapping(strings[0]).toJson());
            e.setArgs(args);
            e.setFuns(funs);
            return e;
        }
        //----------------------控制台输出--------------
        if (str.startsWith(Keywords.KEY_println)&&str.endsWith(Keywords.KEY_println_end)){
            e.setType(Keywords.KEY_println);
            funs.add(mapping(StringTool.getCenter(str,Keywords.KEY_println,Keywords.KEY_println_end)).toJson());
            e.setFuns(funs);
            return e;
        }
        //---------------------字符串相加----------------
        if(str.startsWith(Keywords.KEY_String_start)&&str.endsWith(Keywords.KEY_String_end)){

            final String[] strings = StringTool.binString(str, Keywords.KEY_String_center, 0, Keywords.KEY_String_start, Keywords.KEY_String_end);
            funs.add(mapping(strings[0]).toJson());
            funs.add(mapping(strings[1]).toJson());
            e.setType(Keywords.KEY_String_start);
            e.setFuns(funs);
            return e;
        }
        //-------------------------函数调用---------------
        if (str.startsWith(Keywords.KEY_function_run)&&str.endsWith(Keywords.KEY_function_run_end)){
            final String center = StringTool.getCenter(str, Keywords.KEY_function_run, Keywords.KEY_function_run_end);
            final String[] split = center.split(":");
            if(split.length!=1){
                //有参数
                String[] vars = split[1].split(",");
                for (String var : vars) {
                    args.add(mapping(var.trim()).toJson());
                }
                e.setArgs(args);
            }
            e.setType(Keywords.KEY_function_run);
            e.setName(split[0].trim());
            return e;
        }
        //---------------------变量自增自减---------------------
        if (str.startsWith(Keywords.KEY_math_begin)&&(str.endsWith(Keywords.KEY_math_jia)||str.endsWith(Keywords.KEY_math_jian))){
            final String type = str.endsWith(Keywords.KEY_math_jia) ? Keywords.KEY_math_jia : Keywords.KEY_math_jian;
            e.setType(Keywords.KEY_math_begin);
            final String center = StringTool.getCenter(str, Keywords.KEY_math_begin, type);
            String s;
            if (center.startsWith("\"")&& center.endsWith("\"")){
                //是一个常量
                s = Element.getValueElement(StringTool.remove_yin_hao(center)).toJson();

            }else{
                //变量
                s = Element.getkeyElement(center).toJson();
            }
            args.add(type);
            e.setArgs(args);
            funs.add(s);
            e.setFuns(funs);
            return e;

        }
        //---------------------获取变量值
        e.setName(str);
        e.setType("key");
        return e;
    }



}
