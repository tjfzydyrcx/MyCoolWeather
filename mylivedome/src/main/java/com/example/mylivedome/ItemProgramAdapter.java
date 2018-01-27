package com.example.mylivedome;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 2018-01-27 0027.
 */
public class ItemProgramAdapter extends BaseAdapter {
    private Context mContext;
    ViewHolder viewHolder;
    private String[] mDataList = new String[]{
            "香港卫视", "CCTV1高清", "CCTV3高清", "CCTV5+高清", "CCTV5+高清", "CCTV6高清"
    };
    private String[] mUrlList = new String[]{
            "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8", "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8", "http://ivi.bupt.edu.cn/hls/cctv3hd.m3u8",
            "http://ivi.bupt.edu.cn/hls/cctv5hd.m3u8", "http://ivi.bupt.edu.cn/hls/cctv5phd.m3u8", "http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8"
    };

    public ItemProgramAdapter(Context mContext) {
        this.mContext = mContext;
        viewHolder = new ViewHolder();
    }

    @Override
    public int getCount() {
        return mDataList.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview, parent, false);
        TextView tvNmae = viewHolder.get(convertView, R.id.tv_name);
        tvNmae.setText(mDataList[position]);

        tvNmae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LiveActivity.class);
                intent.putExtra("url", mUrlList[position]);
                intent.putExtra("title", mDataList[position]);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
}
