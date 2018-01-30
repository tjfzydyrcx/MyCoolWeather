package com.example.mylivedome;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by Administrator on 2018-01-27 0027.
 */
public class LiveActivity extends AppCompatActivity {

    private String mUrl;
    private String mTilte;
    private RelativeLayout mRootView;
    private LinearLayout mTop_lay;
    private LinearLayout mBottom_lay;
    private RelativeLayout rl_layout;
    private ImageView img_back;
    private ImageView img_play;
    private TextView tv_title;
    private TextView mtime;
    private VideoView mVdeoView;
    public static final int RETRY_TIMES = 5;
    private int mCount = 0;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = LiveActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_live);
        mUrl = getIntent().getStringExtra("url");
        mTilte = getIntent().getStringExtra("title");
        initView();
        initEvent();
        initPlayer();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        //屏幕切换时，设置全屏
        if (mVdeoView != null) {
            mVdeoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        }
        super.onConfigurationChanged(newConfig);

    }

    private void initView() {
        mRootView = (RelativeLayout) findViewById(R.id.activity_live);
        mTop_lay = (LinearLayout) findViewById(R.id.ll_toplayout);
        rl_layout = (RelativeLayout) findViewById(R.id.rl_layout);
        mBottom_lay = (LinearLayout) findViewById(R.id.ll_Bottomlayout);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_play = (ImageView) findViewById(R.id.img_play);
        tv_title = (TextView) findViewById(R.id.live_title);
        mtime = (TextView) findViewById(R.id.live_time);
        if (!TextUtils.isEmpty(mTilte)) {
            tv_title.setText(mTilte);
        }
        mtime.setText(GetCurrentTime());

    }

    private void initPlayer() {
        Vitamio.isInitialized(getApplicationContext());
        mVdeoView = (VideoView) findViewById(R.id.surface_view);
        mVdeoView.setVideoURI(Uri.parse(mUrl));
        mVdeoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVdeoView.start();
            }
        });

        mVdeoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (mCount > RETRY_TIMES) {
                    new AlertDialog.Builder(LiveActivity.this).setMessage("播放出错").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LiveActivity.this.finish();
                        }
                    }).setCancelable(false).show();
                } else {
                    mVdeoView.stopPlayback();
                    mVdeoView.setVideoURI(Uri.parse(mUrl));
                    mVdeoView.start();
                }
                mCount++;
                return false;
            }
        });


        mVdeoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {

                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        rl_layout.setVisibility(View.VISIBLE);
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                        rl_layout.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }

    private String GetCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("HH:mm");
        String time = df.format(calendar.getTime());
        return time;

    }

    private void initEvent() {

        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTop_lay.getVisibility() == View.VISIBLE || mBottom_lay.getVisibility() == View.VISIBLE) {
                    mTop_lay.setVisibility(View.GONE);
                    mBottom_lay.setVisibility(View.GONE);
                    return;
                }
                if (mVdeoView.isPlaying()) {
                    img_play.setImageResource(R.mipmap.bt_start);
                } else {
                    img_play.setImageResource(R.mipmap.bt_pause);
                }
                mTop_lay.setVisibility(View.VISIBLE);
                mBottom_lay.setVisibility(View.VISIBLE);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTop_lay.setVisibility(View.GONE);
                        mBottom_lay.setVisibility(View.GONE);
                    }
                }, 5000);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVdeoView.isPlaying()) {
                    mVdeoView.pause();
                    img_play.setImageResource(R.mipmap.bt_pause);
                } else {
                    img_play.setImageResource(R.mipmap.bt_start);
                    mVdeoView.start();

                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCount = 0;
        if (mVdeoView != null) {
            mVdeoView.stopPlayback();
        }
    }
}
