package com.example.mygobang;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.example.mygobang.RecyclePage.MyMainActivity;

// 继承自Object类
public class AndroidtoJs extends Object {
    Context context;

    public AndroidtoJs(Context context) {
        this.context = context;
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void hello(String msg) {
        System.out.println("JS调用了Android的hello方法");
        Intent intent = new Intent(context, MyMainActivity.class);
        context.startActivity(intent);

    }
}