package com.example.tjf.mycoolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.tjf.mycoolweather.Mygson.Weather;
import com.example.tjf.mycoolweather.db.Picphoto;
import com.example.tjf.mycoolweather.util.HttpUtil;
import com.example.tjf.mycoolweather.util.LogUtils;
import com.example.tjf.mycoolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017-12-25 0025.
 */
public class BeiduAddress_Service extends Service {
    public LocationClient mLocationClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListerner());
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setScanSpan(60000); //5秒执行一次定位
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyLocationListerner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n");
            currentPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");
            currentPosition.append("定位方式：");
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                currentPosition.append("GPS");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                currentPosition.append("网络");

            }

            LogUtils.e("address_" + currentPosition.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.start();
    }
}
