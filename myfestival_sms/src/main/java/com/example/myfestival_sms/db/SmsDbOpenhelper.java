package com.example.myfestival_sms.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myfestival_sms.Bean.SendMsg;

/**
 * Created by Administrator on 2018-01-25 0025.
 */
public class SmsDbOpenhelper extends SQLiteOpenHelper {
    public static final String DB_MAME = "sms.db";
    public static final int DB_VERSION = 1;

    public SmsDbOpenhelper(Context context) {
        super(context.getApplicationContext(), DB_MAME, null, DB_VERSION);
    }

    private static SmsDbOpenhelper smsDbOpenhelper;

    public static SmsDbOpenhelper getInstance(Context context) {
        if (smsDbOpenhelper == null) {
            synchronized (SmsDbOpenhelper.class) {
                if (smsDbOpenhelper == null) {
                    smsDbOpenhelper = new SmsDbOpenhelper(context);
                }
            }
        }
        return smsDbOpenhelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + SendMsg.TABLE_NAME + " ( " + " _id integer primary key autoincrement , "
                + SendMsg.COLUMN_DATA + " integer ," + SendMsg.COLUMN_FESTIVLANAME + " text , "
                + SendMsg.COLUMN_MSG + " text , " + SendMsg.COLUMN_NAME + " text , " + SendMsg.COLUMN_NUMBER + " text " + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
