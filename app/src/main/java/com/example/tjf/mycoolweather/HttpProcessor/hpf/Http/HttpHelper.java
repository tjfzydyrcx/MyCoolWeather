package com.example.tjf.mycoolweather.HttpProcessor.hpf.Http;

import com.example.tjf.mycoolweather.HttpProcessor.hpf.interfaces.ICallBack;
import com.example.tjf.mycoolweather.HttpProcessor.hpf.interfaces.IhttpProcessor;

import java.util.Map;

/**
 * Created by Administrator on 2018-08-03 0003.
 */

public class HttpHelper implements IhttpProcessor {

    private static IhttpProcessor mIhttpProcessor;
    private static HttpHelper _instance;
    private HttpHelper() {

    }

    public static HttpHelper obtain() {
        if (_instance == null) {
            synchronized (_instance) {
                if (_instance == null) {
                    _instance = new HttpHelper();
                }
            }
        }
        return _instance;
    }

    public static void init(IhttpProcessor httpProcessor) {
        mIhttpProcessor = httpProcessor;
    }


    @Override
    public void get(String url, Map<String, Object> params, ICallBack callBack) {
        mIhttpProcessor.get(url, params, callBack);
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallBack callBack) {
        mIhttpProcessor.post(url, params, callBack);
    }


}
