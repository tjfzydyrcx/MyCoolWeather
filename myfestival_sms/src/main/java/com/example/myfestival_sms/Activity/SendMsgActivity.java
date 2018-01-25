package com.example.myfestival_sms.Activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfestival_sms.Bean.Festival;
import com.example.myfestival_sms.Bean.FestivalLab;
import com.example.myfestival_sms.Bean.Msg;
import com.example.myfestival_sms.Bean.SendMsg;
import com.example.myfestival_sms.Bean.SmsBiz;
import com.example.myfestival_sms.R;
import com.example.myfestival_sms.View.FlowLayout;

import java.util.HashSet;

/**
 * Created by Administrator on 2018-01-25 0025.
 */
public class SendMsgActivity extends AppCompatActivity {
    public static final String FESTIVAL_ID = "festivalid";
    public static final String MSG_ID = "msgid";
    public static final int CODE_RESUEST = 1;
    private LayoutInflater mInflater;

    FloatingActionButton mFabbutton;
    EditText mEdMsg;
    Button mSend;
    FlowLayout mFlowLayout;
    FrameLayout mLoading;
    private int mFestivalid;
    private int mMsgid;
    private Festival mFestival;
    private Msg mMsg;

    private HashSet<String> mContactNames = new HashSet<>();
    private HashSet<String> mContactNums = new HashSet<>();
    public static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
    public static final String ACTION_DELIVER_MSG = "ACTION_DELIVER_MSG";
    private PendingIntent mSendPi;
    private PendingIntent mDeliverPi;
    private BroadcastReceiver mSendBroadcastReceiver;
    private BroadcastReceiver mDeliverBroadcastReceiver;
    private SmsBiz mSms;
    private int mMsgSendCount = 0;
    private int mTotalCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_mag);
        mSms = new SmsBiz(this);
        mInflater = LayoutInflater.from(this);
        initDatas();
        initView();
        initEvent();

        initReceivers();

    }

    private void initReceivers() {

        Intent sendIntent = new Intent(ACTION_SEND_MSG);
        mSendPi = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
        Intent deliverIntent = new Intent(ACTION_DELIVER_MSG);
        mDeliverPi = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);

        registerReceiver(mSendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mMsgSendCount++;
                if (getResultCode() == RESULT_OK) {
                    Log.e(" SendMsg", "成功");
                } else {
                    Log.e(" SendMsg", "失败");
                }
                if (mMsgSendCount == mTotalCount) {
                    mLoading.setVisibility(View.GONE);
                    mMsgSendCount = 0;
                    finish();
                }
            }
        }, new IntentFilter(ACTION_SEND_MSG));

        registerReceiver(mDeliverBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.e(" Deliver", "成功");

            }
        }, new IntentFilter(ACTION_DELIVER_MSG));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSendBroadcastReceiver);
        unregisterReceiver(mDeliverBroadcastReceiver);
    }

    private void initEvent() {
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, CODE_RESUEST);
            }
        });
        mFabbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContactNums.size() == 0) {
                    Toast.makeText(SendMsgActivity.this, "请先添加联系人", Toast.LENGTH_SHORT).show();
                    return;
                }
                String msg = mEdMsg.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(SendMsgActivity.this, "短信内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mLoading.setVisibility(View.VISIBLE);
                mTotalCount = mSms.sendMsg(mContactNums, buildSendMsg(msg), mSendPi, mDeliverPi);
                mMsgSendCount = 0;

            }


        });
    }

    private SendMsg buildSendMsg(String msg) {
        SendMsg smg = new SendMsg();
        smg.setMsg(msg);
        smg.setFestivalName(mFestival.getName());
        String names = "";
        for (String name : mContactNames) {
            names += name + ":";
        }
        smg.setNames(names.substring(0, names.length() - 1));
        String numbers = "";
        for (String number : mContactNums) {
            numbers += number + ":";
        }
        smg.setNumbers(numbers.substring(0,numbers.length() - 1));
        return smg;
    }

    private void initDatas() {
        mFestivalid = getIntent().getIntExtra(FESTIVAL_ID, -1);
        mMsgid = getIntent().getIntExtra(MSG_ID, -1);
        mFestival = FestivalLab.getmInstance().getFestivalByid(mFestivalid);
        setTitle(mFestival.getName());

    }

    private void initView() {
        mEdMsg = (EditText) findViewById(R.id.ed_content);
        mSend = (Button) findViewById(R.id.bt_add);
        mFlowLayout = (FlowLayout) findViewById(R.id.flowlayout);
        mFabbutton = (FloatingActionButton) findViewById(R.id.id_floatingActionButton);
        mLoading = (FrameLayout) findViewById(R.id.id_layout_loading);
        mLoading.setVisibility(View.GONE);
        if (mMsgid != -1) {
            mMsg = FestivalLab.getmInstance().getMsgByid(mFestivalid, mMsgid);
            Log.e("shuju==", mMsg.toString());
            mEdMsg.setText(mMsg.getContent());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_RESUEST) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                cursor.moveToFirst();
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String mNums = getContactNumber(cursor);
                if (!TextUtils.isEmpty(mNums)) {
                    mContactNames.add(contactName);
                    mContactNums.add(mNums);
                    addTag(contactName);
                }
            }
        }
    }

    private void addTag(String contactName) {

        TextView v = (TextView) mInflater.inflate(R.layout.flowlayout_item, mFlowLayout, false);
        v.setText(contactName);
        mFlowLayout.addView(v);

    }

    private String getContactNumber(Cursor cursor) {
        int numberCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        String number = null;
        if (numberCount > 0) {
            int contactid = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Log.e("shuju==", contactid + "");
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactid, null, null);
            phoneCursor.moveToFirst();
            number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneCursor.close();
        }
        cursor.close();
        return number;
    }

    public static void toActivity(Context context, int festivalid, int msgid) {
        Intent intent = new Intent(context, SendMsgActivity.class);
        intent.putExtra(FESTIVAL_ID, festivalid);
        intent.putExtra(MSG_ID, msgid);
        context.startActivity(intent);

    }
}
