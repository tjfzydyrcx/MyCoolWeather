package com.example.mylivedome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView mlistView;
    ItemProgramAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        madapter = new ItemProgramAdapter(this);
        mlistView = (ListView) findViewById(R.id.listView);
        mlistView.setAdapter(madapter);
    }
}
