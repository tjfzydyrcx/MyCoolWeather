package com.example.tjf.mycoolweather.HttpProcessor.hpf.Http;

import com.example.tjf.mycoolweather.HttpProcessor.hpf.interfaces.ICallBack;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2018-08-03 0003.
 */

public abstract class HttpCallBack<T> implements ICallBack {
    @Override
    public void onSuccess(String result) {

        Gson gson = new Gson();
        Class<?> cls = analysisClazzInfo(this);
        T objResult = (T) gson.fromJson(result, cls);
        onSuccess(objResult);
    }

    public static Class<?> analysisClazzInfo(Object object) {

        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

    public abstract void onSuccess(T result);
}
