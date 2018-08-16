package com.example.tjf.mycoolweather;

import android.content.Context;

import com.example.tjf.mycoolweather.HttpProcessor.hpf.Http.HttpHelper;
import com.example.tjf.mycoolweather.HttpProcessor.hpf.processor.Okhttp3Processor;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;


/**
 * Created by Administrator on 2017-12-23 0023.
 */
public class MeApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpHelper.init(new Okhttp3Processor());
    }
}
