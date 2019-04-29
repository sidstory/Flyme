package cn.sidstory.flyme.util;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"3110148276d0c1ffa06980a95fa60b08");
    }
}
