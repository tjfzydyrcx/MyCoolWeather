package com.example.mygobang.RecyclePage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mygobang.R;

public class TitleViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_content;

    public TitleViewHolder(View itemView) {
        super(itemView);
        tv_content = (TextView) itemView.findViewById(R.id.tv_content);
    }
}