package com.example.tjf.mycoolweather.handler;

import android.app.Notification;
import android.os.Looper;
import android.os.MessageQueue;

/**
 * https://blog.csdn.net/zhangke3016/article/details/75923323
 * 自己动手撸一个Handler
 * Created by Administrator on 2018-07-16 0016.
 */

public class Handler {
    MyMessageQueue mQueue;
    MyLooper mLooper;

    public Handler() {
        mLooper = MyLooper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException("");
        }
        mQueue = mLooper.queue;

    }

    public final void sendMessage(MyMessage msg) {
        MyMessageQueue q = mQueue;
        if (q != null) {
            msg.target = this;
            q.enqueueMessage(msg);
        } else {
            RuntimeException e = new RuntimeException();
            throw e;
        }
    }

    public void handleMessage(MyMessage msg) {

    }

    public void dispathMessage(MyMessage msg) {
        handleMessage(msg);
    }
}
