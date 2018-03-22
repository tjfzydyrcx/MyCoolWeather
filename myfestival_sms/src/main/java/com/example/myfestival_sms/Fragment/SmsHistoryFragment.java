package com.example.myfestival_sms.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfestival_sms.Bean.SendMsg;
import com.example.myfestival_sms.R;
import com.example.myfestival_sms.View.FlowLayout;
import com.example.myfestival_sms.db.SmsProvider;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-01-25 0025.
 */
public class SmsHistoryFragment extends ListFragment {
    public static final int LOADER_ID = 1;
    private LayoutInflater mInflater;

    private CursorAdapter mCursorAdapter;
    int i = 0;
    List<String> datas = new ArrayList<>();

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
                View v = null;
                if (v == null) {
                    v = mInflater.inflate(R.layout.item_send_msg, parent, false);
                }
                return v;
            }


            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView msg = ViewHolder.get(view, R.id.tv_msg);
                TagFlowLayout flowLayout = ViewHolder.get(view, R.id.id_flowlayout);
                TextView tvfes = ViewHolder.get(view, R.id.tv_festival);
                TextView tvdate = ViewHolder.get(view, R.id.tv_date);
                msg.setText(cursor.getString(cursor.getColumnIndex(SendMsg.COLUMN_MSG)));
                tvfes.setText(cursor.getString(cursor.getColumnIndex(SendMsg.COLUMN_FESTIVLANAME)));
                long dateVal = cursor.getLong(cursor.getColumnIndex(SendMsg.COLUMN_DATA));
                tvdate.setText(parseDate(dateVal));
                String names = cursor.getString(cursor.getColumnIndex(SendMsg.COLUMN_NAME));
                if (TextUtils.isEmpty(names)) {
                    return;
                }
                ++i;
                String[] b = names.split(":");
                for (String name : b) {
                    datas.clear();
//                    addtag(name, flowLayout);
                    datas.add(name);
                }
                MyTagAdapter taadapter = new MyTagAdapter(datas);
                flowLayout.setAdapter(taadapter);

                flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, com.zhy.view.flowlayout.FlowLayout parent) {
                        Toast.makeText(getActivity(), getItem(position).toString() + "", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
        };
        setListAdapter(mCursorAdapter);

    }

    public static class ViewHolder {
        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(), position + "===", Toast.LENGTH_SHORT).show();


    }

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private String parseDate(long dateVal) {
        return df.format(dateVal);
    }

    private void addtag(String name, FlowLayout flowlayout) {
        TextView tv = (TextView) mInflater.inflate(R.layout.flowlayout_item, flowlayout, false);
        Log.e("jiazai=", ++i + "==");
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

    private static class MyTagAdapter extends TagAdapter<String> {

        public MyTagAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public View getView(com.zhy.view.flowlayout.FlowLayout parent, int position, String s) {
            final TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.flowlayout_item, parent, false);
            tv.setText(s);
            return tv;
        }
    }
}
