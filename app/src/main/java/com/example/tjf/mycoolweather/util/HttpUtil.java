package com.example.tjf.mycoolweather.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017-12-23 0023.
 */
public class HttpUtil {

  /*  public Handler mHandle = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    // 获得对象实例的方法
    public static HttpUtil getInstance() {
        return HttpUtilHolder.mhttputils;
    }

    *//**
     * 静态内部类与外部类的实例没有绑定关系，而且只有被调用时才会
     * 加载，从而实现了延迟加载
     *//*
    private static class HttpUtilHolder {
        private static HttpUtil mhttputils = new HttpUtil();
    }
*/
    public static void sendOkHttpRequest(String address, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public interface MyCallback {
        void onSuccess(String response);

        void onFail(IOException e);
    }
}
