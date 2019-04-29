package cn.sidstory.flyme.util;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class Tips {
    public void showTips(View view, SharedPreferences sharedPreferences) {

        if (sharedPreferences.getBoolean("check", true) && !isModuleActive()) {
            Snackbar.make(view, "您还未启用该模块，设置不会生效。", Snackbar.LENGTH_SHORT).setAction("开启",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            ComponentName componentName = new ComponentName("de.robv.android.xposed.installer", "de.robv.android.xposed.installer.WelcomeActivity");
                            try {
                                intent.setComponent(componentName);
                                v.getContext().startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(v.getContext(), "您还没安装xposed呢", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).show();
        }
    }


    private boolean isModuleActive() {
        return false;
    }

    public static void KillUtil(final String string) {
               CommandExecution.execCommand("a" + "m force-stop " + string, true);




    }
}
