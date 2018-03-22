package com.example.mygobang.RecyclePage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mygobang.R;

public class ListViewHolder extends RecyclerView.ViewHolder {

    public RectImageView iv_icon;
    public TextView tv_content;

    public ListViewHolder(View itemView) {
        super(itemView);
        iv_icon = (RectImageView) itemView.findViewById(R.id.iv_icon);
        tv_content = (TextView) itemView.findViewById(R.id.tv_content);
    }
}