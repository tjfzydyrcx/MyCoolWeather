package com.example.tjf.mycoolweather.HttpProcessor.hpf.processor;

import android.net.Uri;
import android.os.Handler;

import com.example.tjf.mycoolweather.HttpProcessor.hpf.interfaces.ICallBack;
import com.example.tjf.mycoolweather.HttpProcessor.hpf.interfaces.IhttpProcessor;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

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
        mOkHttpClient = getClient();
        mHandle = new Handler();
    }

    @Override
    public void get(String url, Map<String, Object> params, final ICallBack callBack) {
        url= appendParams( url,  params);
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
    /**
     * get方式字符中URL的拼接
     */
    private String appendParams(String url, Map<String, Object> params)
    {
        if (url == null || params == null || params.isEmpty())
        {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key).toString());
        }
        return builder.build().toString();
    }
    private RequestBody appendBody(Map<String, Object> params) {
        FormBody.Builder body = new FormBody.Builder();
        if (params == null || params.isEmpty()) {
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
        if (isSuccessful == true) {
            try {
                result[0] = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            result[0] = response.message().toString();
        }
        mHandle.post(new Runnable() {
            @Override
            public void run() {
                if (isSuccessful == true) {
                    callBack.onSuccess(result[0]);
                } else {
                    callBack.onFailed(result[0]);
                }
            }
        });

    }

    public synchronized OkHttpClient getClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            try {
                // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
                final X509TrustManager trustAllCert =
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        };
                final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
                builder.sslSocketFactory(sslSocketFactory, trustAllCert);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            mOkHttpClient = builder.build();
        }
        return mOkHttpClient;
    }
}
