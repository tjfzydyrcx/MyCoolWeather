package com.example.tjf.mycoolweather.HttpManager;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2018-03-23 0023.
 */

public interface IResquestManager {

    void get(String url, Map<String, String> maps, final XCallBack callback);

    void post(String url, Map<String, String> maps, final XCallBack callback);

    void downFile(String url, String filePath, final XDownLoadCallBack callback);

    void upLoadFile(String url, Map<String, String> maps, Map<String, File> file, final XCallBack callback);

    //接口回调
    public interface XCallBack<T> {
        void onResponse(T result);

        void onFail(String result);
    }

    //下载的接口回调
    public interface XDownLoadCallBack<T> {
        void onstart();

        void onLoading(long total, long current, boolean isDownloading);

        void onSuccess(T result);

        void onFail(String result);

        void onFinished();
    }
}
