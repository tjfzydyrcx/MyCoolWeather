package com.example.myfestival_sms.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfestival_sms.Bean.SendMsg;
import com.example.myfestival_sms.R;
import com.example.myfestival_sms.View.FlowLayout;
import com.example.myfestival_sms.db.SmsProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2018-01-25 0025.
 */
public class SmsHistoryFragment extends ListFragment {
    public static final int LOADER_ID = 1;
    private LayoutInflater mInflater;

    private CursorAdapter mCursorAdapter;
    int i = 0;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInflater = LayoutInflater.from(getActivity());
        initLoader();
        setupListAdapter();
    }

    private void setupListAdapter() {
        mCursorAdapter = new CursorAdapter(getActivity(), null, false) {


            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View view = null;
                if (view == null)
                    view = mInflater.inflate(R.layout.item_send_msg, parent, false);
                return view;
            }
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView msg = (TextView) view.findViewById(R.id.tv_msg);
                FlowLayout flowLayout = (FlowLayout) view.findViewById(R.id.id_flowlayout);
                TextView tvfes = (TextView) view.findViewById(R.id.tv_festival);
                TextView tvdate = (TextView) view.findViewById(R.id.tv_date);
                msg.setText(cursor.getString(cursor.getColumnIndex(SendMsg.COLUMN_MSG)));
                tvfes.setText(cursor.getString(cursor.getColumnIndex(SendMsg.COLUMN_FESTIVLANAME)));
                long dateVal = cursor.getLong(cursor.getColumnIndex(SendMsg.COLUMN_DATA));
                tvdate.setText(parseDate(dateVal));
                String names = cursor.getString(cursor.getColumnIndex(SendMsg.COLUMN_NAME));
                if (TextUtils.isEmpty(names)) {
                    return;
                }
                for (String name : names.split(":")) {
                    addtag(name, flowLayout);
                    Log.e("jiazai=", ++i + "==");
                }

            }
        };
        setListAdapter(mCursorAdapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


    }

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private String parseDate(long dateVal) {
        return df.format(dateVal);
    }

    private void addtag(String name, FlowLayout flowlayout) {
        TextView tv = (TextView) mInflater.inflate(R.layout.flowlayout_item, flowlayout, false);
        tv.setText(name);
        flowlayout.addView(tv);

    }

    private void initLoader() {
        getLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader loader = new CursorLoader(getActivity(), SmsProvider.URI_SMS_ALL, null, null, null, null);
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                if (loader.getId() == LOADER_ID) {
                    mCursorAdapter.swapCursor(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mCursorAdapter.swapCursor(null);
            }
        });

    }
}
