package com.example.hjl.jgpushtest;

import android.app.Application;

import org.litepal.LitePalApplication;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by hjl on 2017/5/5.
 */

public class  App extends LitePalApplication {
    private static App instance = null;
    public static App getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        当 Application《APP》没有继承LitePalApplication 可以加入下面代码
//        LitePalApplication.initialize(this);
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
    }
}
