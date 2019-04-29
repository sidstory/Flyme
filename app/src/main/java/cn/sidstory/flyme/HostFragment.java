package cn.sidstory.flyme;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.android.material.snackbar.Snackbar;
import com.suke.widget.SwitchButton;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.sidstory.flyme.util.CommandExecution;


public class HostFragment extends Fragment implements SwitchButton.OnCheckedChangeListener {

    SwitchButton switch_host = null;
    View view = null;
    Context context = null;
    SharedPreferences setting = null;
    SharedPreferences.Editor editor = null;
    //SweetAlertDialog dialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host, container, false);
        context = container.getContext();
        setting = getContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
        editor = setting.edit();
        switch_host = view.findViewById(R.id.switch_host);
        initData();
        switch_host.setOnCheckedChangeListener(this);
        getActivity().setTitle("Host净化");
        return view;

    }


    private void initData() {
        if (setting.getBoolean("host", false)) {
            switch_host.setChecked(true);

        }

    }

    private void reboot() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Process process = null;
                DataOutputStream os = null;
                try {
                    process = Runtime.getRuntime().exec("su -c reboot");

                    os.close();
                } catch (Exception e) {
                    Log.d("host", "mv: " + e.getStackTrace().toString());
                }

            }
        }).start();
    }

    private void rebootTips() {
        Snackbar.make(getActivity().findViewById(R.id.setting_fragment), "host需要重启后才生效，重启吗？", Snackbar.LENGTH_LONG).setAction("立即重启", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reboot();
            }
        }).show();
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        if (view.getId() == R.id.switch_host) {
            editor.putBoolean("host", isChecked);
            editor.commit();
            if (isChecked) {

                new MyTask().execute("hosts_browser");

            } else {
                new MyTask().execute("hosts_default");
            }

        }
    }

        class MyTask extends AsyncTask<String, Void, Integer> {
            SweetAlertDialog pDialog = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("正在执行，请稍后");
                pDialog.setCancelable(false);
                pDialog.show();
            }


            @Override
            protected Integer doInBackground(String... strings) {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try {
                    InputStream inputStream = context.getAssets().open(strings[0]);
                    FileOutputStream fileOutputStream = new FileOutputStream("/data/data/cn.sidstory.flyme/cache/hosts_browser");
                    int tempt = 0;
                    while ((tempt = inputStream.read()) != (-1)) {
                        fileOutputStream.write(tempt);
                    }
                    fileOutputStream.close();
                    inputStream.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                CommandExecution.CommandResult commandResult = CommandExecution.execCommand(new String[]{"mount -o rw,remount /system", "mv /data/data/cn.sidstory.flyme/cache/hosts_browser /system/etc/"}, true);
                int result = commandResult.getResult();

                return result;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                pDialog.dismiss();
                if (integer==0){
                    rebootTips();
                }
                else SmartToast.fail("获取权限失败，设置不会生效");


            }


        }
    }


