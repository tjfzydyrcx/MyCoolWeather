package com.example.myapplication.UpdateApk;

import android.os.AsyncTask;
import android.os.Environment;


import com.example.myapplication.LogUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2017-12-26 0026.
 */
public class DownloadTask extends AsyncTask<String, Integer, Integer> {
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCLEED = 3;
    private DownLoadListener mloadListener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public DownloadTask(DownLoadListener mloadListener) {
        this.mloadListener = mloadListener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        RandomAccessFile saveFile = null;
        File file = null;
        try {
            long downloadelength = 0;
            String dowmloadUrl = params[0];
            String fileName = dowmloadUrl.substring(dowmloadUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory, fileName);
            if (file.exists()) {
                downloadelength = file.length();
                LogUtils.e("progress==" + downloadelength);
            }
            long contentLength = getContentLength(dowmloadUrl);
       /*     RandomAccessFile randomAccessFile = new RandomAccessFile(
                    file, "rw");
            randomAccessFile.setLength(contentLength);// 设置随机访问文件的大小,本地的占位文件
            randomAccessFile.close();*/

            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (contentLength == downloadelength) {
                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().addHeader("RANGE", "bytes=" + downloadelength + "-").url(dowmloadUrl).build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                is = response.body().byteStream();
                saveFile = new RandomAccessFile(file, "rw");
                saveFile.seek(downloadelength);//移动到下载之前的位置
                byte[] b = new byte[1024*2];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCLEED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += len;
                        saveFile.write(b, 0, len);
                        //计算下载的百分比
                        int progress = (int) ((total + downloadelength) * 100 / contentLength);
                        //更新进度
                        publishProgress(progress);
                        LogUtils.e("progress==" + total + "===" + downloadelength + "===" + contentLength);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                if (is != null) {
                    is.close();
                }
                if (saveFile != null) {
                    saveFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int progres = values[0];
        if (progres > lastProgress) {

            mloadListener.onProgress(progres);
            lastProgress = progres;
        }

    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        switch (integer) {
            case TYPE_SUCCESS:
                mloadListener.onSuccess();
                break;
            case TYPE_FAILED:
                mloadListener.onFailed();
                break;
            case TYPE_PAUSED:
                mloadListener.onPaused();
                break;
            case TYPE_CANCLEED:
                mloadListener.onCanceled();
                break;
            default:
                break;
        }
    }

    public void pauseDownload() {
        isPaused = true;
    }

    public void cancelDownload() {
        isCanceled = true;
    }

    private long getContentLength(String dowmloadUrl) throws IOException {

        OkHttpClient.Builder client = new OkHttpClient.Builder().connectTimeout(3000, TimeUnit.SECONDS);

        Request reqeust = new Request.Builder().url(dowmloadUrl).build();
        Response response = client.build().newCall(reqeust).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            return contentLength;
        }
        return 0;
    }
}
