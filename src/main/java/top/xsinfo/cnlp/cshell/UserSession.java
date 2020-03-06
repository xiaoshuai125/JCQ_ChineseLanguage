package top.xsinfo.cnlp.cshell;


import top.xsinfo.cnlp.cshell.common.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Properties;

/**
 * 用户会话类
 * 这里保存着用户的基本信息
 */
public class UserSession {
    private Log log = Core.getLog();
    private long id;
    private File file;

    private Properties properties ;



    /** 创建一个用户会话 **/
    public UserSession(long name){
        this.id = name;
        properties = new Properties();
        file = new File("userInfo");
        if (!file.exists())file.mkdir();

        file = new File("userInfo/"+name+".properties");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.logE("用户"+id+"createUserSession",e.getMessage());
                e.printStackTrace();
            }
        }

        try (FileReader fileReader = new FileReader(file)){
            properties.load(fileReader);


        } catch (IOException e) {
            log.logE("用户"+id+"loadUserSession",e.getMessage());
            e.printStackTrace();
        }


    }


    /** 获取全局变量 **/
    public String getProperties(String key) {
        String str = properties.getProperty(key);
        if (str != null) {
            return str;
        }
        return "null" ;
    }


    /** 设置全局变量 **/
    public UserSession setProperties(String key,String o) {

        properties.setProperty(key,o.trim());

        try (FileWriter fileWriter = new FileWriter(file)){

            properties.store(fileWriter,"This is my data");
        } catch (IOException e) {
            log.logE("用户"+id+"setProperty",e.getMessage());
            e.printStackTrace();
        }
        return this;
    }


}
