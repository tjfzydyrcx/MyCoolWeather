package com.example.myapplication.UpdateApk;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;


import com.example.myapplication.DownloadActivity;
import com.example.myapplication.R;
import com.example.myapplication.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017-12-26 0026.
 */
public class DownloadService extends Service {
    private DownloadTask downloadTask;
    private String downloadUrl;


    //实例化下载回调监听
    private DownLoadListener downLoadListener = new DownLoadListener() {
        @Override
        public void onProgress(int progress) {
            //更新通知下载进度
            getNotificationManager().notify(1, getNotification("Downloading...", progress));
            ToastUtil.showLong(DownloadService.this, "Download ===" + progress);
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            stopForeground(true);//下载成功，通知前台服务关闭，并创建一个下载成功的通知
            getNotificationManager().notify(1, getNotification("Download success", -1));
            ToastUtil.showLong(DownloadService.this, "Download Success");
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Failed", -1));
            ToastUtil.showLong(DownloadService.this, "Download Failed");
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            ToastUtil.showLong(DownloadService.this, "Download pause");
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            ToastUtil.showLong(DownloadService.this, "Download canceled");

        }
    };

    /**
     * 开始下载
     * 根据待下载文件大小计算每个线程下载位置，并创建AsyncTask
     */
    private void beginDownload() {

    }

    private DownloadBinder mBinder = new DownloadBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public class DownloadBinder extends Binder {


        /**
         * @param url
         */
        public void startDownload(String url) {
            if (downloadTask == null) {
                downloadUrl = url;
                downloadTask = new DownloadTask(downLoadListener);
                downloadTask.execute(downloadUrl);
                startForeground(1, getNotification("Downloading...", 0));
                ToastUtil.showLong(DownloadService.this, "Doloading..");




            }
        }

        /**
         * 暂停下载
         */
        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            }
        }

        /**
         * 取消下载删除下载中的文件，并关闭通知
         */
        public void cancelDownload() {
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            }
            if (downloadUrl != null) {
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory, fileName);
                if (file.exists()) {
                    file.delete();
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    ToastUtil.showLong(DownloadService.this, "canceled");
                }
            }
        }
    }

    /**
     * 得到通知管理
     *
     * @return
     */
    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * 得到通知
     *
     * @param title
     * @param progress
     * @return
     */
    private Notification getNotification(String title, final int progress) {
        Intent intent = new Intent(getApplication(), DownloadActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle(title);
        if (progress >= 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        builder.setContentIntent(pi);
        return builder.build();
    }


}
