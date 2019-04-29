package cn.sidstory.flyme.xposed;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage {
    private static String MODULE_PATH;
    private static boolean first = false;
    private static int resId;
    final XSharedPreferences xSharedPreferences = new XSharedPreferences("cn.sidstory.flyme", "config");

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        xSharedPreferences.makeWorldReadable();
        xSharedPreferences.reload();
        if (lpparam.packageName.equals("com.meizu.flyme.update") && (xSharedPreferences.getBoolean("update", false))) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass1 = null;
                    Class<?> hookclass2 = null;
                    Class<?> hookclass3 = null;
                    Class<?> hookclass4 = null;
                    try {
                        hookclass1 = cl.loadClass("com.meizu.flyme.update.common.d.b");
                        hookclass2 = cl.loadClass("");
                        hookclass3 = cl.loadClass("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    XposedHelpers.findAndHookMethod(hookclass1, "g", Context.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(false);
                            XposedBridge.log("系统更新 hook 成功");
                        }
                    });

                }
            });
        }


        if (lpparam.packageName.equals("com.android.packageinstaller") && (xSharedPreferences.getBoolean("check", false))) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass1 = null;
                    Class<?> hookclass2 = null;
                    Class<?> hookclass3 = null;
                    Class<?> hookclass4 = null;
                    try {
                        hookclass1 = cl.loadClass("com.android.packageinstaller.FlymePackageInstallerActivity");
                        if (hookclass1 == null) {
                            hookclass1 = cl.loadClass("com.android.packageinstaller.PackageInstallerActivity");
                        }
                        hookclass2 = cl.loadClass("");
                        hookclass3 = cl.loadClass("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    XposedHelpers.findAndHookMethod(hookclass1, "setVirusCheckTime", new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                            XposedHelpers.callMethod(XposedHelpers.getObjectField(param.thisObject, "mHandler"), "sendEmptyMessage", new Object[]{Integer.valueOf(5)});
                            return null;
                        }
                    });
                }
            });
        }

        if (lpparam.packageName.equals("com.meizu.mznfcpay") && (xSharedPreferences.getBoolean("pay", false))) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass1 = null;
                    Class<?> hookclass2 = null;
                    Class<?> hookclass3 = null;
                    Class<?> hookclass4 = null;
                    try {
                        hookclass1 = cl.loadClass("com.meizu.cloud.a.a.a");
                        hookclass2 = cl.loadClass("");
                        hookclass3 = cl.loadClass("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    XposedHelpers.findAndHookMethod(hookclass1, "c", Context.class,XC_MethodReplacement.returnConstant(Boolean.valueOf(false)));
                }

            });
        }
        if (lpparam.packageName.equals("com.android.systemui")) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass1 = null;
                    Class<?> hookclass2 = null;
                    Class<?> hookclass3 = null;
                    Class<?> hookclass4 = null;
                    try {
                        hookclass1 = cl.loadClass("com.android.systemui.statusbar.phone.StatusBarHeaderView");
                        hookclass2 = cl.loadClass("com.meizu.keyguard.UnderScreenFingerprint.UnderScreenFingerprintControl");
                        hookclass3 = cl.loadClass("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    final Class<?> finalHookclass = hookclass1;
                    if (xSharedPreferences.getBoolean("replace",false)){
                    XposedHelpers.findAndHookMethod(hookclass1, "updateClockCollapsedMargin", new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            Field editButton = XposedHelpers.findField(finalHookclass, "mQSTilesEditButton");
                            editButton.setAccessible(true);
                            final ImageView view = (ImageView) editButton.get(param.thisObject);
                            view.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    XposedHelpers.callMethod(param.thisObject,"startSettingsActivity");
                                    return false;
                                }
                            });
                        }
                    });
                    }
                    if (xSharedPreferences.getBoolean("unlock",false)){
                    XposedHelpers.findAndHookMethod(hookclass2, "isFirstBootUnlock", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(true);
                        }
                    });

                }
                }

            });

        }

        if (lpparam.packageName.equalsIgnoreCase("com.flyme.systemuitools") && (xSharedPreferences.getBoolean("game", false))) {

            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass1 = null;
                    Class<?> hookclass2 = null;
                    Class<?> hookclass3 = null;
                    Class<?> hookclass4 = null;
                    try {
                        hookclass1 = cl.loadClass("com.flyme.systemuitools.gamemode.ProViewControler");
                        hookclass2 = cl.loadClass("com.flyme.systemuitools.gamemode.ProViewControler$CallBack");
                        hookclass3 = cl.loadClass("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    final Class<?> finalHookclass1 = hookclass1;
                    XposedHelpers.findAndHookMethod(hookclass1, "prepareShow", boolean.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            Field mTabNoti = XposedHelpers.findField(finalHookclass1, "mTabNoti");
                            mTabNoti.setAccessible(true);
                            FrameLayout frameLayout = (FrameLayout) mTabNoti.get(param.thisObject);
                            frameLayout.callOnClick();
                        }

                });
                    XposedBridge.hookAllConstructors(hookclass1, new XC_MethodHook() {
                       @Override
                       protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                           super.afterHookedMethod(param);
                           Field mTabGame = XposedHelpers.findField(finalHookclass1, "mTabGame");
                           mTabGame.setAccessible(true);
                           FrameLayout gamewalfare = (FrameLayout) mTabGame.get(param.thisObject);
                           gamewalfare.setVisibility(View.GONE);

                       }
                   });


                }
            });

        }


        if (lpparam.packageName.equalsIgnoreCase("com.android.browser") && (xSharedPreferences.getBoolean("browser", false))) {

            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    final ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass1 = null;
                    Class<?> hookclass2 = null;
                    Class<?> hookclass3 = null;
                    Class<?> hookclass4 = null;
                    try {
                        hookclass1 = cl.loadClass("com.android.browser.pages.BrowserHomeFragment$PagerAdapter");
                        hookclass2 = cl.loadClass("");
                        hookclass3 = cl.loadClass("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    final Class<?> finalHookclass1 = hookclass1;
                    XposedHelpers.findAndHookMethod(finalHookclass1, "getCount", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(new Integer(1));
                        }
                    });

                    XposedHelpers.findAndHookMethod(finalHookclass1, "getCurrentPagePosition", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(0);
                        }
                    });

                    XposedHelpers.findAndHookMethod(hookclass1, "isInZixunPage", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.setResult(false);
                        }
                    });


                }
        });
        }

        if (lpparam.packageName.equals("cn.sidstory.flyme")) {
            XposedHelpers.findAndHookMethod("cn.sidstory.flyme.util.Tips", lpparam.classLoader, "isModuleActive", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(true);
                }
            });
        }
    }
/*

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        xSharedPreferences.makeWorldReadable();
        xSharedPreferences.reload();
        if (resparam.packageName.equals("com.android.systemui")){

            XModuleResources resources=XModuleResources.createInstance(MODULE_PATH,resparam.res);
            resId = resparam.res.addResource(resources, R.drawable.ic_settings_white);

        }

    }


    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH=startupParam.modulePath;

    }
    */

}






