package com.example.hjl.jgpushtest;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by hjl on 2017/5/5.
 */

public class  App extends Application {
    private static App instance = null;
    public static App getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
    }
}
