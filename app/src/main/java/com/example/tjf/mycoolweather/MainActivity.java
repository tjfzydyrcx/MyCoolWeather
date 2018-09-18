package com.example.tjf.mycoolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.tjf.mycoolweather.HttpProcessor.hpf.Http.HttpCallBack;
import com.example.tjf.mycoolweather.HttpProcessor.hpf.Http.HttpHelper;
import com.example.tjf.mycoolweather.HttpProcessor.hpf.processor.Okhttp3Processor;
import com.example.tjf.mycoolweather.service.BeiduAddress_Service;
import com.example.tjf.mycoolweather.widgets.FoldTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by Administrator on 2018-01-26 0026.
 */
public class MainActivity extends BaseActivity {
    //哈哈哈
    private String url2 = "http://www.kuaidi100.com/query?type=quanfengkuaidi&postid=300008026630";
   /*  @BindView(R.id.text_fold)
    FoldTextView foldTextView;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent intent1 = new Intent(this, BeiduAddress_Service.class);
        startService(intent1);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getString("weather", null) != null) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }
     /*   foldTextView.setText("挖方为安慰法无法无法为违法挖坟阿瓦发违法而发违法无法无法为发我发我发我发疯安慰法发违法违法无法无法 发我发我发发我发我发份万维发发发 阿发违法安慰法阿发啊发发发发阿瓦发违法违法违法无法无法访问发二分法分为分为发哇违法违法微风阿瓦违法威风啊娃娃而发违法违法阿尔法微风安慰法外");

        HttpHelper.obtain().get(url2, null, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onFailed(String string) {

            }
        });*/
      /*  DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        TouchImageView imgView = (TouchImageView) findViewById(R.id.id_image);
        imgView.initImageView(dm.widthPixels, dm.heightPixels - 80);*/
    }



}
