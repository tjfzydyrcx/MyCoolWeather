package com.example.myphotonine;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by Administrator on 2018-05-09 0009.
 */

public class MyjavaScript {
    private static final String TAG = "MyjavaScript";
    javainterface java;

    public MyjavaScript(javainterface java) {
        this.java = java;
    }

    @JavascriptInterface
    public void show(String value) {
        Log.d(TAG, "value==" + value);
        java.setTextStrig(value);
    }
}
