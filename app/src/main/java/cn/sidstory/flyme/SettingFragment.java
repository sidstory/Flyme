package cn.sidstory.flyme;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.android.material.snackbar.Snackbar;
import com.suke.widget.SwitchButton;

import cn.sidstory.flyme.util.CommandExecution;
import cn.sidstory.flyme.util.Tips;

import static cn.sidstory.flyme.util.Tips.KillUtil;


public class SettingFragment extends Fragment implements SwitchButton.OnCheckedChangeListener {
    private static boolean ROOTSTATE=false;
    SharedPreferences preferences;
    private SwitchButton switch_meizupay;
    private SwitchButton switch_taobao;
    private SwitchButton switch_root_update;
    private SwitchButton switch_safer_check;
    private SwitchButton switch_search;
    private SwitchButton switch_replace;
    private SwitchButton switch_unlock;
    private SwitchButton switch_game;
    private SwitchButton switch_browser;
    SharedPreferences config;//主界面
    SharedPreferences.Editor edit;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                SmartToast.error("获取root失败，设置不会生效");
            }
        }
    };

    View view = null;
    Context context = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        config = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences = context.getSharedPreferences("system", Context.MODE_PRIVATE);
        switch_meizupay = view.findViewById(R.id.switch_meizupay);
        switch_taobao = view.findViewById(R.id.switch_taobao);
        switch_root_update = view.findViewById(R.id.switch_root_updata);
        switch_safer_check = view.findViewById(R.id.switch_safer_check);
        switch_search = view.findViewById(R.id.switch_search);
        switch_replace = view.findViewById(R.id.switch_replace_setting);
        switch_unlock=view.findViewById(R.id.switch_unlock);
        switch_game=view.findViewById(R.id.switch_game);
        switch_browser=view.findViewById(R.id.switch_browser);
        edit = config.edit();//主界面
        initData();
        switch_unlock.setOnCheckedChangeListener(this);
        switch_meizupay.setOnCheckedChangeListener(this);
        switch_taobao.setOnCheckedChangeListener(this);
        switch_root_update.setOnCheckedChangeListener(this);
        switch_safer_check.setOnCheckedChangeListener(this);
        switch_search.setOnCheckedChangeListener(this);
        switch_replace.setOnCheckedChangeListener(this);
        switch_game.setOnCheckedChangeListener(this);
        switch_browser.setOnCheckedChangeListener(this);
        getActivity().setTitle("魅工具");
        return view;
    }


    private void initData() {

        if (config.getBoolean("pay", false)) {
            switch_meizupay.setChecked(true);
        }

        if (config.getBoolean("taobao", false)) {
            switch_taobao.setChecked(true);
        }
        if (config.getBoolean("update", false)) {
            switch_root_update.setChecked(true);
        }
        if (config.getBoolean("search", false)) {
            switch_search.setChecked(true);
        }
        if (config.getBoolean("check", false)) {
            switch_safer_check.setChecked(true);
        }

        if (config.getBoolean("replace", false)) {
            switch_replace.setChecked(true);
        }
        if (config.getBoolean("unlock", false)) {
            switch_unlock.setChecked(true);
        }
        if (config.getBoolean("game", false)) {
            switch_game.setChecked(true);
        }
        if (config.getBoolean("browser", false)) {
            switch_browser.setChecked(true);
        }

    }


    private void isModuleActive() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        new Tips().showTips(view, sharedPreferences);
    }

    private int taobaoFix() {
        CommandExecution.CommandResult commandResult = CommandExecution.execCommand(new String[]{"rm -rf /data/data/com.taobao.taobao/files/bundleBaseline"/** "mkdir /data/data/com.taobao.taobao/files/bundleBaseline", "chmod 400   /data/data/com.taobao.taobao/files/bundleBaseline"*/}, true);
        return commandResult.getResult();
    }

    private void taobaoFixTip() {
        Snackbar.make(getActivity().findViewById(R.id.setting_fragment), "修复完成，进淘宝看看吧", Snackbar.LENGTH_SHORT).setAction("去看看", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponentName componentName = new ComponentName("com.taobao.taobao", "com.taobao.tao.homepage.MainActivity3");
                Intent intent = new Intent();
                try {
                    intent.setComponent(componentName);
                    getActivity().startActivity(intent);
                } catch (Exception e) {
                    SmartToast.fail("打开失败");
                }

            }
        }).show();
    }

    public static void  permissionFix() {
        CommandExecution.execCommand(new String[]{ "chmod  777  /data/data/cn.sidstory.flyme","chmod  777  /data/data/cn.sidstory.flyme/shared_prefs/config.xml","chmod  777  /data/data/cn.sidstory.flyme/shared_prefs"}, true);
        }


    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        isModuleActive();
        switch (view.getId()) {
            case R.id.switch_root_updata: {
                edit.putBoolean("update", isChecked);
               edit.commit();
                KillUtil("com.meizu.flyme.update");
                permissionFix();
                break;
            }


            case R.id.switch_meizupay: {
                edit.putBoolean("pay", isChecked);
                edit.commit();
                KillUtil("com.meizu.mznfcpay");
                permissionFix();
                break;
            }
            case R.id.switch_unlock: {
                edit.putBoolean("unlock", isChecked);
                edit.commit();
                KillUtil("com.android.systemui");
                permissionFix();
                break;
            }
            case R.id.switch_browser: {
                edit.putBoolean("browser", isChecked);
                edit.commit();
                KillUtil("com.android.browser");
                permissionFix();
                break;
            }
            case R.id.switch_search: {
                edit.putBoolean("search",isChecked);
                edit.commit();
                if (isChecked) {
                    CommandExecution.execCommand("pm disable com.meizu.net.search", true);
                } else CommandExecution.execCommand("pm enable com.meizu.net.search", true);
                //KillUtil.kill("com.meizu.mznfcpay");
                permissionFix();
                break;
            }
            case R.id.switch_safer_check: {
                edit.putBoolean("check", isChecked);
                edit.commit();
                KillUtil("com.android.packageinstaller");
                permissionFix();
                break;
            }
            case R.id.switch_replace_setting: {
                edit.putBoolean("replace", isChecked);
                edit.commit();
                permissionFix();
                break;
            }
            case R.id.switch_game: {
                edit.putBoolean("game", isChecked);
                edit.commit();
                KillUtil("com.flyme.systemuitools");
                edit.commit();
                permissionFix();
                break;
            }
            case R.id.switch_taobao: {
                edit.putBoolean("taobao", isChecked);
                edit.commit();
                KillUtil("com.taobao.taobao");
                int i = taobaoFix();
                if (i != 0) {
                    SmartToast.fail("获取root失败，设置不会生效");
                } else {
                    taobaoFixTip();
                }
                permissionFix();
                break;
            }
        }
    }


}


