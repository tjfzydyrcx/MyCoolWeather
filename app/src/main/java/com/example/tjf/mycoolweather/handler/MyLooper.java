package com.example.tjf.mycoolweather.handler;

import android.os.Looper;
import android.support.v4.app.NavUtils;
import android.util.TimeFormatException;

/**
 * Created by Administrator on 2018-07-16 0016.
 */

public class MyLooper {

    static final ThreadLocal<MyLooper> sThreadLocal = new InheritableThreadLocal<>();

    MyMessageQueue queue;

    private MyLooper() {
        queue = new MyMessageQueue();
    }

    public static MyLooper myLooper() {

        return sThreadLocal.get();
    }

    public static void prepare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("");
        }
        sThreadLocal.set(new MyLooper());
    }

    public static void loop(){
        MyLooper looper=myLooper();
        if (looper==null){
            throw new RuntimeException("");

        }

        MyMessageQueue queue=looper.queue;

        for (;;){
            MyMessage msg=queue.next();
            if(msg==null || msg.target ==null){
                continue;
            }
            msg.target.dispathMessage(msg);

        }
    }
}
