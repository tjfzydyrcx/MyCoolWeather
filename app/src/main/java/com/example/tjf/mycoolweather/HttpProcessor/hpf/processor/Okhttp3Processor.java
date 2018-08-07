package com.example.tjf.mycoolweather.HttpProcessor.hpf.processor;

import android.os.Handler;

import com.example.tjf.mycoolweather.HttpProcessor.hpf.interfaces.ICallBack;
import com.example.tjf.mycoolweather.HttpProcessor.hpf.interfaces.IhttpProcessor;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018-08-03 0003.
 */

public class Okhttp3Processor implements IhttpProcessor {

    public static OkHttpClient mOkHttpClient;
    private Handler mHandle;

    public Okhttp3Processor() {
        mOkHttpClient = new OkHttpClient();
        mHandle = new Handler();
    }

    @Override
    public void get(String url, Map<String, Object> params, final ICallBack callBack) {
        Request request = new Request.Builder().url(url).build();
        OkCall(request, callBack);
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallBack callBack) {
        RequestBody requestBody = appendBody(params);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        OkCall(request, callBack);
    }

    private void OkCall(Request request, final ICallBack callBack) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean isSuccessful = response.isSuccessful();
                postParams(callBack, isSuccessful, response);
            }
        });
    }

    private RequestBody appendBody(Map<String, Object> params) {
        FormBody.Builder body = new FormBody.Builder();
        if (params != null || params.isEmpty()) {
            return body.build();
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            body.add(entry.getKey(), entry.getValue().toString());
        }
        return body.build();
    }

    ;

    private void postParams(final ICallBack callBack, final boolean isSuccessful, final Response response) {
        final String[] result = {""};
        mHandle.post(new Runnable() {
            @Override
            public void run() {
                if (isSuccessful == true) {
                    result[0] = response.body().toString();
                    callBack.onSuccess(result[0]);
                } else {
                    result[0] = response.message().toString();
                    callBack.onFailed(result[0]);
                }
            }
        });

    }
}
