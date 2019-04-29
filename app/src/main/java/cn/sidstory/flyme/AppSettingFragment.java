package cn.sidstory.flyme;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.suke.widget.SwitchButton;

import androidx.fragment.app.Fragment;
import cn.sidstory.flyme.util.Tips;


public class AppSettingFragment extends Fragment implements SwitchButton.OnCheckedChangeListener {
    View view = null;
    Context context = null;
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor=null;
    SwitchButton switch_setting_hideicon;
    SwitchButton switch_setting_check;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_app_setting, container, false);
        context = container.getContext();
        sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        switch_setting_check = view.findViewById(R.id.switch_appsetting_check);
        switch_setting_hideicon = view.findViewById(R.id.switch_appsetting_hideicon);
        initData();
        switch_setting_hideicon.setOnCheckedChangeListener(this);
        switch_setting_check.setOnCheckedChangeListener(this);
        getActivity().setTitle("应用设置");
        return view;
    }

    private void initData() {
        if (sharedPreferences.getBoolean("hideicon",false))
        {
            switch_setting_hideicon.setChecked(true);
        }

        if (sharedPreferences.getBoolean("check",true))
        {
            switch_setting_check.setChecked(true);
        }

    }

    public void hideIcon(boolean hide) {
        getContext().getPackageManager().getComponentEnabledSetting(new ComponentName(getContext(), "cn.sidstory.flyme.SplashActivity"));
        PackageManager packageManager = getContext().getPackageManager();
        ComponentName componentName = new ComponentName(getContext(), "cn.sidstory.flyme.SplashActivity");
        if (hide) {
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }else {
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        }
    }


    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (view.getId() == R.id.switch_appsetting_check) {
            edit.putBoolean("check", isChecked);
            edit.commit();
            if (isChecked) {
                new Tips().showTips(view,sharedPreferences);

            }
        } else {
            edit.putBoolean("hideicon", isChecked);
            edit.commit();
            hideIcon(isChecked);
            //getActivity().finish();
        }
    }
}
