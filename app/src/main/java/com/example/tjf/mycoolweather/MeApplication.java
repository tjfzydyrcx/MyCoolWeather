package com.example.tjf.mycoolweather;

import android.app.Application;

import org.litepal.LitePal;


/**
 * Created by Administrator on 2017-12-23 0023.
 */
public class MeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //litePal 的初始化
        LitePal.initialize(this);
    }
}
