package com.example.myphotonine;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2018-05-09 0009.
 */

public class WebViewActivity extends AppCompatActivity implements javainterface {
    WebView webView;
    TextView textView;
    EditText editText;
    Button bt;
    Handler mhandle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);

        init();
    }

    private void init() {
        mhandle = new Handler();
        webView = (WebView) findViewById(R.id.id_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webView.addJavascriptInterface(new MyjavaScript(this), "android");

        webView.loadUrl("file:///android_asset/index.html");


        textView = (TextView) findViewById(R.id.id_tv_text);
        editText = (EditText) findViewById(R.id.ed_content);
        bt = (Button) findViewById(R.id.bt_send);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                webView.loadUrl("javascript:if(window.remote){window.remote('" + str + "')}");
            }
        });
    }

    @Override
    public void setTextStrig(final String value) {
        mhandle.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(value + "");
            }
        });
    }
}
