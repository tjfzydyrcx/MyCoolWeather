package com.example.myfestival_sms.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfestival_sms.Bean.FestivalLab;
import com.example.myfestival_sms.Bean.Msg;
import com.example.myfestival_sms.R;

/**
 * Created by Administrator on 2018-01-25 0025.
 */
public class chooseMsgActivity extends AppCompatActivity {
    private ListView mListView;
    private FloatingActionButton mFabToSend;
    private ArrayAdapter<Msg> mAdapter;
    int mFestivalid;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_msg);
        mFestivalid = getIntent().getIntExtra("festival_id", -1);
        setTitle(FestivalLab.getmInstance().getFestivalByid(mFestivalid).getName());
        mInflater = LayoutInflater.from(this);
        initView();
        initEvent();

    }

    private void initEvent() {

        mFabToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMsgActivity.toActivity(chooseMsgActivity.this, mFestivalid, -1);

            }
        });
    }

    private void initView() {

        mListView = (ListView) findViewById(R.id.listView);
        mFabToSend = (FloatingActionButton) findViewById(R.id.id_floatingActionButton);

        mListView.setAdapter(mAdapter = new ArrayAdapter<Msg>(this, -1, FestivalLab.getmInstance().getMsgListByid(mFestivalid)) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_msg, parent, false);
                }
                TextView tv = (TextView) convertView.findViewById(R.id.textView);
                tv.setText(getItem(position).getContent());
                Button send = (Button) convertView.findViewById(R.id.button);
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SendMsgActivity.toActivity(chooseMsgActivity.this, mFestivalid, getItem(position).getId());
                    }
                });
                return convertView;
            }
        });
    }
}
