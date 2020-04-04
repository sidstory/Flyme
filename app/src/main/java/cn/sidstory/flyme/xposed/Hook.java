package cn.sidstory.flyme.xposed;

import android.app.Application;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.List;

import cn.sidstory.flyme.util.ConfigTool;
import cn.sidstory.flyme.util.HookTool;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {


        if (lpparam.packageName.equals("com.android.systemui")&&ConfigTool.read("unlock")) {
            HookTool.hookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass1 = null;//flyme8版本
                    Class<?> hookclass2 = null;//flyme7
                    Class<?> hookclass3 = null;
                    Class<?> hookclass4 = null;
                    try {
                        hookclass1 = cl.loadClass("com.meizu.keyguard.UnderScreenFingerprint.UnderScreenFingerprintController");

                    } catch (Exception e) {
                        XposedBridge.log(e.getMessage());
                    }
                    try{
                        hookclass2  =cl.loadClass("com.meizu.keyguard.UnderScreenFingerprint.UnderScreenFingerprintControl");
                    }
                    catch(Exception e
                    ){
                        XposedBridge.log(e.getMessage());
                    }

                        HookTool.hookMethod(hookclass1, "isFirstBootUnlock", new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                param.setResult(true);
                            }
                        });
                    HookTool.hookMethod(hookclass2, "isFirstBootUnlock", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(true);
                        }
                    });

                    }

            });

        }


        if (lpparam.packageName.equalsIgnoreCase("com.android.browser") && (ConfigTool.read("browser"))) {

            HookTool.hookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass1 = null;
                    Class<?> hookclass2 = null;
                    Class<?> hookclass3 = null;
                    Class<?> hookclass4 = null;
                    try {
                        hookclass1 = cl.loadClass("com.android.browser.page.fragment.BrowserHomeFragment$a");//pageview
                        hookclass2 = cl.loadClass("com.android.browser.page.fragment.BrowserHomeFragment");//配合上面pageview
                        hookclass3 = cl.loadClass("com.android.browser.view.SuggestionHotWordHeaderView");//搜索热词
                        hookclass4=cl.loadClass("com.android.browser.manager.search.SearchHotWordManager");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    final Class<?> finalHookclass1 = hookclass1;
                    HookTool.hookMethod(finalHookclass1, "getItem",int.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            param.args[0]=0;
                            super.beforeHookedMethod(param);
                        }
                    });


                    HookTool.hookMethod(hookclass1, "getCount", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(1);
                        }
                    });
                    HookTool.hookMethod(hookclass2, "w", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(false);
                        }
                    });
                    HookTool.hookMethod(hookclass2, "getPage", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult("page_home");
                        }
                    });
                    HookTool.hookMethod(hookclass2, "switchToZiXunPage", new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                            return null;
                        }
                    });
                    HookTool.hookMethod(hookclass3, "getHotWordViewVisible", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(-1);
                        }
                    });
                    HookTool.hookMethod(hookclass4, "hotWordListEmpty", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(true);
                        }
                    });

                }
            });
        }
        if (lpparam.packageName.equalsIgnoreCase("com.meizu.flyme.directservice")&&(ConfigTool.read("quick"))) {
                XposedBridge.log(ConfigTool.read("qq")+"");
            HookTool.hookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass1 = null;
                    Class<?> hookclass2 = null;
                    Class<?> hookclass3 = null;
                    Class<?> hookclass4 = null;
                    try {
                        hookclass1 = cl.loadClass("com.meizu.flyme.directservice.common.network.data.MenuConfigBean$Value");
                        hookclass2=cl.loadClass("com.meizu.flyme.directservice.common.network.data.MenuConfigBean$Value$Menu");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    final Class<?> finalHookclass1 = hookclass1;
                    HookTool.hookMethod(hookclass1, "getMenuList", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            Field menuList = finalHookclass1.getField("menuList");
                            List list = (List) menuList.get(param.thisObject);
                            if (list.size()==5){
                                for (int i=0;i<2;i++){
                                    list.remove(list.size()-2);
                                }
                           }

                           param.setResult(list);


                        }
                    });

                }
            });
        }
        if (lpparam.packageName.equalsIgnoreCase("com.tencent.mobileqq")&&(ConfigTool.read("qq"))) {

            HookTool.hookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass1 = null;
                    Class<?> hookclass2 = null;
                    Class<?> hookclass3 = null;
                    Class<?> hookclass4 = null;
                    try {
                        hookclass1 = cl.loadClass("com.tencent.mobileqq.activity.Conversation");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    HookTool.hookMethod(hookclass1, "L", new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                            XposedBridge.log("QQ hooked");
                            return null;
                        }
                    });


                }
            });
        }
        if (lpparam.packageName.equalsIgnoreCase("com.flyme.systemuitools")&&(ConfigTool.read("game"))) {

            HookTool.hookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass1 = null;
                    Class<?> hookclass2 = null;
                    Class<?> hookclass3 = null;
                    Class<?> hookclass4 = null;
                    try {
                        hookclass1 = cl.loadClass("com.flyme.systemuitools.gamemode.ProViewControler");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    final Class<?> finalHookclass = hookclass1;
                    HookTool.hookMethod(hookclass1, "prepareShow", boolean.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            Field mProView = XposedHelpers.findField(finalHookclass, "mTabFrame");
                            mProView.setAccessible(true);
                            LinearLayout view = (LinearLayout) mProView.get(param.thisObject);
                            if (view.getChildCount()==3){
                                view.removeViewAt(1);
                            }

                            Field mTabNoti = XposedHelpers.findField(finalHookclass, "mTabNoti");
                            mTabNoti.setAccessible(true);
                            FrameLayout notice = (FrameLayout) mTabNoti.get(param.thisObject);
                            notice.callOnClick();
                        }
                    });


                }
            });
        }
        if (lpparam.packageName.equalsIgnoreCase("cn.sidstory.flyme")) {

            HookTool.hookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass1 = null;
                    Class<?> hookclass2 = null;
                    Class<?> hookclass3 = null;
                    Class<?> hookclass4 = null;
                    try {
                        hookclass1 = cl.loadClass("cn.sidstory.flyme.util.Tips");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    HookTool.hookMethod(hookclass1, "isModuleActive", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(true);
                        }
                    });


                }
            });
        }
                }
            }





