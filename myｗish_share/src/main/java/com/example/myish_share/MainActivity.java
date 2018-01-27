package com.example.myish_share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MainActivity extends Activity {
    private ImageView mPhoto;
    private EditText mWord;
    private Button mShare;
    private IWXAPI iwxapi;
    private int flog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化
        iwxapi = WXAPIFactory.createWXAPI(this, "APP_ID");
        iwxapi.registerApp("APP_ID");
        initView();
        initEvent();
    }


    private void initView() {
        mPhoto = (ImageView) findViewById(R.id.photo);
        mWord = (EditText) findViewById(R.id.word);
        mWord.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));
        mShare = (Button) findViewById(R.id.bt_share);

    }

    private void initEvent() {
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 100);
            }
        });

        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                wacharShare();
                mPhoto.setImageBitmap(generateSpringCard());
                mShare.setVisibility(View.VISIBLE);
            }
        });
    }

    private void wacharShare() {
        WXWebpageObject webpage = new WXWebpageObject();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.mediaObject = new WXImageObject(generateSpringCard());

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        iwxapi.sendReq(req);

    }

    private Bitmap generateSpringCard() {
        mShare.setVisibility(View.INVISIBLE);
        View view = getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 100 && resultCode == RESULT_OK) {
            mPhoto.setImageURI(data.getData());
        }
    }
}
