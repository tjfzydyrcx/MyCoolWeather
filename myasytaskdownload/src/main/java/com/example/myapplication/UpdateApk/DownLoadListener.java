package com.example.myapplication.UpdateApk;

/**
 * 下载接口的回调
 * Created by Administrator on 2017-12-26 0026.
 */
public interface DownLoadListener {

    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();
}
