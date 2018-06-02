package com.example.tjf.mycoolweather.HttpManager;

import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.os.Looper.getMainLooper;

/**
 * Created by Administrator on 2018-03-26 0026.
 */

public class OkHttputils implements IResquestManager {

    private static OkHttputils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler okHttpHandler;//全局处理子线程和M主线程通信

    private OkHttputils() {
        OkHttpClient.Builder mClientBudiler = new OkHttpClient.Builder();
        mClientBudiler.readTimeout(30, TimeUnit.SECONDS);
        mClientBudiler.connectTimeout(10, TimeUnit.SECONDS);
        mClientBudiler.writeTimeout(60, TimeUnit.SECONDS);
        mOkHttpClient = mClientBudiler.build();
        okHttpHandler = new Handler(getMainLooper());
    }

    public static OkHttputils getmInstance() {
        if (mInstance == null) {
            synchronized (OkHttputils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttputils();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void get(String url, Map<String, String> maps, XCallBack callback) {
        String params = setUrlParams(maps);
        final Request request = new Request.Builder().url(url + params).get().build();
        Call call = getmInstance().mOkHttpClient.newCall(request);
        addCallBack(call, callback);
    }

    @Override
    public void post(String url, Map<String, String> maps, final XCallBack callback) {
        FormBody.Builder mBuilder = new FormBody.Builder();
        for (String key : maps.keySet()) {
            mBuilder.add(key, maps.get(key));
        }
        final Request request = new Request.Builder().url(url).post(mBuilder.build()).build();
        Call call = getmInstance().mOkHttpClient.newCall(request);
        addCallBack(call, callback);
    }

    @Override
    public void downFile(String url, String filePath, XDownLoadCallBack callback) {

    }

    @Override
    public void upLoadFile(String url, Map<String, String> maps, Map<String, File> file, XCallBack callback) {

    }


    /**
     * 统一同意处理成功信息
     *
     * @param result
     * @param callBack
     * @param <T>
     */
    private <T> void successCallBack(final T result, final XCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(result);
                }
            }
        });
    }

    /**
     * 统一处理失败信息
     *
     * @param errorMsg
     * @param callBack
     * @param <T>
     */
    private <T> void failedCallBack(final String errorMsg, final XCallBack<T> callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onFail(errorMsg);
                }
            }
        });
    }

    /**
     * get方法连接拼加参数
     *
     * @param mapParams
     * @return
     */
    private String setUrlParams(Map<String, String> mapParams) {
        String strParams = "";
        if (mapParams != null) {
            Iterator<String> iterator = mapParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                strParams += "&" + key + "=" + mapParams.get(key);
            }
        }

        return strParams;
    }

    /**
     * 统一抽取的请求类异步Call
     * 这里的response ，如果是小于1M 的，这使用response.body().toString()；，反之则用流读取
     *
     * @param call
     * @param callback
     */
    public void addCallBack(Call call, final XCallBack callback) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failedCallBack(e.toString(), callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                successCallBack(response, callback);

            }
        });
    }
}
