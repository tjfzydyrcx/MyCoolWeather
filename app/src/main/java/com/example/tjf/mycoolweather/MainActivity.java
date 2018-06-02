package com.example.tjf.mycoolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import com.example.tjf.mycoolweather.HttpManager.HttpManagers;
import com.example.tjf.mycoolweather.HttpManager.IResquestManager;
import com.example.tjf.mycoolweather.HttpManager.OkHttputils;

import butterknife.ButterKnife;
/**
 * Created by Administrator on 2018-01-26 0026.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getString("weather", null) != null) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }

      /*  DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        TouchImageView imgView = (TouchImageView) findViewById(R.id.id_image);
        imgView.initImageView(dm.widthPixels, dm.heightPixels - 80);*/
    }



}
