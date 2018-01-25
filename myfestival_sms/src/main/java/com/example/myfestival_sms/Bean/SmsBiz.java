package com.example.myfestival_sms.Bean;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.telephony.SmsManager;

import com.example.myfestival_sms.db.SmsProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * Created by Administrator on 2018-01-25 0025.
 */
public class SmsBiz {
    private Context c;

    public SmsBiz(Context c) {
        this.c = c;
    }

    public int sendMsg(String number, String msg, PendingIntent sendPi, PendingIntent DeliverPi) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> cpntents = smsManager.divideMessage(msg);
        for (String content : cpntents) {
            smsManager.sendTextMessage(number, null, content, sendPi, DeliverPi);
        }
        return cpntents.size();
    }


    public int sendMsg(Set<String> numbers, SendMsg msg, PendingIntent sendPi, PendingIntent DeliverPi) {
        save(msg);
        int result = 0;
        for (String number : numbers) {
            int count = sendMsg(number, msg.getMsg(), sendPi, DeliverPi);
            result += count;
        }
        return result;
    }


    private void save(SendMsg sendMsg) {
        sendMsg.setDate(new Date());
        ContentValues values = new ContentValues();
        values.put(SendMsg.COLUMN_DATA, sendMsg.getDate().getTime());
        values.put(SendMsg.COLUMN_FESTIVLANAME, sendMsg.getFestivalName());
        values.put(SendMsg.COLUMN_MSG, sendMsg.getMsg());
        values.put(SendMsg.COLUMN_NAME, sendMsg.getNames());
        values.put(SendMsg.COLUMN_NUMBER, sendMsg.getNumbers());
        c.getContentResolver().insert(SmsProvider.URI_SMS_ALL, values);

    }
}
