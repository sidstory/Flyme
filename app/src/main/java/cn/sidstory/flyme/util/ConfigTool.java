package cn.sidstory.flyme.util;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ConfigTool {
     static Properties properties=new Properties();
     static File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/flymetool/config.prop");
    public static boolean read(String key){
        String value="";
        try (FileInputStream fileInputStream=new FileInputStream(file)){
            properties.load(fileInputStream);
             value = properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (value!=null&&"true".equals(value)){

            return true;
        }
        return false;
    }
    public static void save(String key,boolean state){
        String value="false";
        if (state==true){
            value="true";
        }
        try (FileOutputStream fileOutputStream=new FileOutputStream(file)){
            properties.setProperty(key,value);
            properties.store(fileOutputStream,"this is flyme tool config,do not modify or delete it");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
