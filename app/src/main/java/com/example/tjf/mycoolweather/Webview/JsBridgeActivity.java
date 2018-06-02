package com.example.tjf.mycoolweather.Webview;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.tjf.mycoolweather.R;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

public class JsBridgeActivity extends AppCompatActivity {

    BridgeWebView bridgeWebView;
    Button button;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_bridge);
//         if ()
//        getSupportActionBar().setTitle("JsBridge_test");

        button = (Button) findViewById(R.id.button3);

        bridgeWebView = (BridgeWebView) findViewById(R.id.JsBridgeWebView);

        bridgeWebView.setDefaultHandler(new DefaultHandler());

        bridgeWebView.setWebChromeClient(new WebChromeClient());

        bridgeWebView.setWebViewClient(new MyWebViewClient(bridgeWebView));

        bridgeWebView.loadUrl("file:///android_asset/index.html");
        /**
         * js发送给按住消息   submitFromWeb 是js调用的方法名    安卓\返回给js
         */
        bridgeWebView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                showToast(data);
                //返回给html的消息
                function.onCallBack("返回给Toast的alert");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 给Html发消息   js接收并返回data
                 */
                bridgeWebView.callHandler("functionInJs", "调用js的方法", new CallBackFunction() {

                    @Override
                    public void onCallBack(String data) {

                        showToast("===" + data);
                    }
                });
            }
        });

    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}