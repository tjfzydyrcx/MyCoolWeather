package com.example.tjf.mycoolweather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.tjf.mycoolweather.R;

import java.util.List;

/**
 * Created by Administrator on 2018-03-22 0022.
 */

public class CityAdapter extends BaseAdapter {
    // 自定义数据集与布局加载器
    List<String> citylist;
    Context mContext;

    public CityAdapter(Context mContext, List<String> citylist) {
        this.mContext = mContext;
        this.citylist = citylist;
    }

    @Override
    public int getCount() {
        return citylist.size();
    }

    @Override
    public Object getItem(int position) {
        return citylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.list_item_layout);
        TextView tvTitle = viewHolder.findViewById(R.id.text_City);
        // 设置内容
        tvTitle.setText(citylist.get(position));
        return viewHolder.getConvertView();

    }

}
