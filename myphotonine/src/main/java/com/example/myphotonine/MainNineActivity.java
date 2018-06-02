package com.example.myphotonine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

public class MainNineActivity extends Activity {
    NineGridImageView mNineGridImageView;
    List<String> mlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nine);
        mNineGridImageView = findViewById(R.id.Nine_imgview);
        mlist.add(Uri.parse("android.resource" + "//" + getApplicationContext().getPackageName() + "/" + R.mipmap.paizhao).toString());
        NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String strings) {
                Log.e("tags111==", strings);

                Glide.with(context).load(strings).placeholder(R.mipmap.ic_launcher).into(imageView);
            }

       /*     @Override
            protected void onItemImageClick(Context context, int index, List<String> list) {
                super.onItemImageClick(context, index, list);
                Log.e("ts111==", index + "");
                if (list.size() - 1 == index) {
                    Log.e("ts222==", index + "");

                            PhotoPicker.builder()
                                    .setPhotoCount(1)
                                    .setShowCamera(false)
                                    .setShowGif(true)
                                    .setPreviewEnabled(true)
                                    .start(MainNineActivity.this, 300);


                }
            }*/
        };
        mNineGridImageView.setAdapter(mAdapter);

        mNineGridImageView.setImagesData(mlist);
        Log.e("tags==", mlist.get(0));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int height = this.getWindowManager().getDefaultDisplay().getHeight();
        int width = this.getWindowManager().getDefaultDisplay().getWidth();
        if (resultCode == RESULT_OK) {
            if (requestCode == 300) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                Log.e("lijigs=", photos.get(0));
            } else if (requestCode == 200) {

            }
        }

    }

}
