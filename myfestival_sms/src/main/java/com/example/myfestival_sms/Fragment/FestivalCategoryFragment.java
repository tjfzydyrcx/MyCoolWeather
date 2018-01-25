package com.example.myfestival_sms.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.myfestival_sms.Bean.Festival;
import com.example.myfestival_sms.Bean.FestivalLab;
import com.example.myfestival_sms.R;
import com.example.myfestival_sms.Activity.chooseMsgActivity;

/**
 * Created by Administrator on 2018-01-25 0025.
 */
public class FestivalCategoryFragment extends Fragment {
    private GridView mGridView;
    private ArrayAdapter<Festival> mAdapter;
    private LayoutInflater mInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_festival_category, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mInflater=LayoutInflater.from(getActivity());
        mGridView = (GridView) view.findViewById(R.id.id_gridview);
        mGridView.setAdapter(mAdapter = new ArrayAdapter<Festival>(getActivity(), -1, FestivalLab.getmInstance().getmFestivals()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_category, parent,false);
                }
                TextView tv = (TextView) convertView.findViewById(R.id.text_festival);
                tv.setText(getItem(position).getName());
                return convertView;
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),chooseMsgActivity.class);
                intent.putExtra("festival_id",mAdapter.getItem(position).getId());
                startActivity(intent);

            }
        });

    }
}
