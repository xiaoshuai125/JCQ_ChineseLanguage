package top.xsinfo.cnlp.cshell.common;


import java.util.List;

/** 字符串操作工具类 **/
public class StringTool {
    /** 取字符串中间 **/
    public static String getCenter(String str, String start, String end){
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb.substring(sb.indexOf(start)+start.length(),sb.lastIndexOf(end)).trim();
    }

    public static List<String> trimStrings(List<String> strings){
        for (int i = 0; i < strings.size(); i++) {
            strings.set(i,strings.get(i).trim()) ;
        }
        return strings;
    }
    public static String[] trimStrings(String[] strings){
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].trim();
        }
        return strings;
    }

    /** 去掉引号 **/
    public static String remove_yin_hao(String str){
        if (str.startsWith("\"")&& str.endsWith("\"")){
            return StringTool.getCenter(str,"\"","\"");
        }
        return str.trim();
    }



    /**二分替换取字符串**/
    public static String[] binString(String str ,String center,int index,String begin,String end){
        String[] strs = new String[2];
        int center1 = str.indexOf(center, index);
        strs[0]=str.substring(0,center1);
        strs[0]=strs[0].substring(begin.length());
        strs[1]=str.substring(center1+center.length());
        strs[1]=strs[1].substring(0,strs[1].lastIndexOf(end));
        return strs;
    }

    /**二分替换取字符串**/
    public static String[] binString(String str ,String center,int index){
        String[] strs = new String[2];
        int center1 = str.indexOf(center, index);
        strs[0]=str.substring(0,center1).trim();
        strs[1]=str.substring(center1+center.length()).trim();
        return strs;
    }


}
