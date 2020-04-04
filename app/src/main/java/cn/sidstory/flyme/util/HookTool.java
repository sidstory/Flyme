package cn.sidstory.flyme.util;


import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HookTool  {
    public static void hookMethod(Class clazz, String methodName, XC_MethodHook methodHook){
        try{
            XposedHelpers.findAndHookMethod(clazz,methodName,methodHook);
        }
        catch (Exception e){
            XposedBridge.log(e);

        }

    }
    public static void hookMethod(Class clazz, String methodName,Class parameter, XC_MethodHook methodHook){
        try{
            XposedHelpers.findAndHookMethod(clazz,methodName,parameter,methodHook);
        }
        catch (Exception e){
            XposedBridge.log(e);
        }

    }
    public static void hookMethod(Class clazz, String methodName,Class parameter,Class parameter1, XC_MethodHook methodHook){
        try{
            XposedHelpers.findAndHookMethod(clazz,methodName,parameter,parameter1,methodHook);
        }
        catch (Exception e){
            XposedBridge.log(e);
        }

    }
    public static void hookMethod(String clazz,ClassLoader classLoader,String methodName,XC_MethodHook methodHook){
        try{
            XposedHelpers.findAndHookMethod(clazz,classLoader,methodName,methodHook);
        }
        catch (Exception e){
            XposedBridge.log(e);
        }
    }
    public static void hookMethod(String clazz,ClassLoader classLoader,String methodName,Class parameter,XC_MethodHook methodHook){
        try{
            XposedHelpers.findAndHookMethod(clazz,classLoader,methodName,parameter,methodHook);
        }
        catch (Exception e){
            XposedBridge.log(e);
        }
    }
    public static void hookMethod(String clazz,ClassLoader classLoader,String methodName,Class parameter,Class parameter1,XC_MethodHook methodHook){
        try{
            XposedHelpers.findAndHookMethod(clazz,classLoader,methodName,parameter,parameter1,methodHook);
        }
        catch (Exception e){
            XposedBridge.log(e);
        }
    }
}
