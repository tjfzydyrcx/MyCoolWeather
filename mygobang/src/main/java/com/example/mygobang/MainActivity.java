package com.example.mygobang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    GobangView gobangView;
    PopWinShare popWinShare;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gobangView = (GobangView) findViewById(R.id.gobangView);
        tv = (TextView) findViewById(R.id.textView);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWinShare == null) {
                    popWinShare = new PopWinShare(MainActivity.this, new OnClickLintener(),
                            DisplayUtil.dip2px(MainActivity.this, 100), DisplayUtil.dip2px(MainActivity.this, 100));
                    //监听窗口的焦点事件，点击窗口外面则取消显示
                    popWinShare.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                popWinShare.dismiss();
                            }
                        }
                    });
                }
                //设置默认获取焦点
                popWinShare.setFocusable(true);
                //以某个控件的x和y的偏移量位置开始显示窗口
                popWinShare.showAsDropDown(tv, 0, 10);
                //如果窗口存在，则更新
                popWinShare.update();
            }
        });
    }


    class OnClickLintener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_start:
                    gobangView.Start();
                    popWinShare.dismiss();
                    break;
                case R.id.layout_copy:
//                  BouncingMenu.makeMenu(R.layout.activity_main,1).show();
                    break;

                default:
                    break;
            }

        }

    }
}



