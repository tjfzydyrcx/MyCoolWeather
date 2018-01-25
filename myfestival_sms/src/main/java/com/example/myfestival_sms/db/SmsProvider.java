package com.example.myfestival_sms.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.myfestival_sms.Bean.SendMsg;

/**
 * Created by Administrator on 2018-01-25 0025.
 */
public class SmsProvider extends ContentProvider {


    private static final String AUTJPROTY = "com.example.myfestival_sms.provider.SmsProvider";
    public static final Uri URI_SMS_ALL = Uri.parse("content://" + AUTJPROTY + "/sms");
    private static UriMatcher matcher;
    private static final int SMS_ALL = 0;
    private static final int SMS_ONE = 1;

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTJPROTY, "sms", SMS_ALL);
        matcher.addURI(AUTJPROTY, "sms/#", SMS_ONE);
    }

    private SmsDbOpenhelper smsDbOpenhelper;
    private SQLiteDatabase mDb;


    @Override
    public boolean onCreate() {

        smsDbOpenhelper = SmsDbOpenhelper.getInstance(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = matcher.match(uri);
        switch (match) {
            case SMS_ALL:
                break;
            case SMS_ONE:

                long id = ContentUris.parseId(uri);
                selection = "_id = ? ";
                selectionArgs = new String[]{String.valueOf(id)};
                break;
            default:
                throw new IllegalArgumentException("Wrong URI:" + uri);
        }
        mDb = smsDbOpenhelper.getReadableDatabase();
        Cursor c = mDb.query(SendMsg.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        c.setNotificationUri(getContext().getContentResolver(), URI_SMS_ALL);
        return c;
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = matcher.match(uri);
        if (match != SMS_ALL) {
            throw new IllegalArgumentException("Wrong URI:" + uri);
        }
        mDb = smsDbOpenhelper.getWritableDatabase();
        long rowid = mDb.insert(SendMsg.TABLE_NAME, null, values);
        if (rowid > 0) {
            notityDataSetChanged();
            return ContentUris.withAppendedId(uri, rowid);
        }
        return null;
    }

    private void notityDataSetChanged() {

        getContext().getContentResolver().notifyChange(URI_SMS_ALL, null);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
