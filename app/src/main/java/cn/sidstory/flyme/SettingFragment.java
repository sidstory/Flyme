package cn.sidstory.flyme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.suke.widget.SwitchButton;

import cn.sidstory.flyme.util.CommandExecution;
import cn.sidstory.flyme.util.ConfigTool;
import cn.sidstory.flyme.util.Tips;

import static cn.sidstory.flyme.util.Tips.KillUtil;


public class SettingFragment extends Fragment implements SwitchButton.OnCheckedChangeListener {
    private static boolean ROOTSTATE=false;
    SharedPreferences preferences;
    private SwitchButton switch_search;//下拉搜索
    private SwitchButton switch_replace;
    private SwitchButton switch_unlock;//开机指纹
    private SwitchButton switch_game;//游戏
    private SwitchButton switch_browser;//浏览器
    private SwitchButton switch_quick;//快应用
    private SwitchButton switch_qq;//qq
    SharedPreferences config;//主界面
    SharedPreferences.Editor edit;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                Toast.makeText(getContext(),"获取root失败，设置不会生效" , Toast.LENGTH_SHORT).show();
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
        switch_search = view.findViewById(R.id.switch_search);
        switch_replace = view.findViewById(R.id.switch_replace_setting);
        switch_unlock=view.findViewById(R.id.switch_unlock);
        switch_game=view.findViewById(R.id.switch_game);
        switch_browser=view.findViewById(R.id.switch_browser);
        switch_quick=view.findViewById(R.id.switch_quick);
        switch_qq=view.findViewById(R.id.switch_qq);
        edit = config.edit();//主界面
        initData();
        switch_unlock.setOnCheckedChangeListener(this);
        switch_search.setOnCheckedChangeListener(this);
        switch_replace.setOnCheckedChangeListener(this);
        switch_game.setOnCheckedChangeListener(this);
        switch_browser.setOnCheckedChangeListener(this);
        switch_quick.setOnCheckedChangeListener(this);
        switch_qq.setOnCheckedChangeListener(this);
        getActivity().setTitle(R.string.app_name);
        return view;
    }


    private void initData() {

        if (ConfigTool.read("search")) {
            switch_search.setChecked(true);
        }

        if (ConfigTool.read("replace")) {
            switch_replace.setChecked(true);
        }
        if (ConfigTool.read("unlock")) {
            switch_unlock.setChecked(true);
        }
        if (ConfigTool.read("game")) {
            switch_game.setChecked(true);
        }
        if (ConfigTool.read("browser")) {
            switch_browser.setChecked(true);
        }
        if (ConfigTool.read("quick")) {
            switch_quick.setChecked(true);
        }
        if (ConfigTool.read("qq")) {
            switch_qq.setChecked(true);
        }
    }


    private void isModuleActive() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        new Tips().showTips(view, sharedPreferences);
    }



    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        isModuleActive();
        switch (view.getId()) {
            case R.id.switch_unlock: {
                ConfigTool.save("unlock",isChecked);
                KillUtil("com.android.systemui");
                break;
            }
            case R.id.switch_browser: {
                ConfigTool.save("browser",isChecked);
                KillUtil("com.android.browser");
                break;
            }
            case R.id.switch_quick: {
                ConfigTool.save("quick", isChecked);
                KillUtil("com.meizu.flyme.directservice");
                break;
            }
            case R.id.switch_qq: {
                ConfigTool.save("qq", isChecked);
                KillUtil("com.tencent.mobileqq");
                break;
            }
            case R.id.switch_search: {
                ConfigTool.save("search",isChecked);
                if (isChecked) {
                    CommandExecution.execCommand("pm disable com.meizu.net.search", true);
                } else CommandExecution.execCommand("pm enable com.meizu.net.search", true);
                break;
            }

            case R.id.switch_replace_setting: {
                ConfigTool.save("replace", isChecked);
                break;
            }
            case R.id.switch_game: {
                ConfigTool.save("game", isChecked);
                KillUtil("com.flyme.systemuitools");
                break;
            }

        }
    }


}


