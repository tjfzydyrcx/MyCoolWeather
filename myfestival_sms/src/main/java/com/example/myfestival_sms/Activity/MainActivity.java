package com.example.myfestival_sms.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.example.myfestival_sms.Fragment.FestivalCategoryFragment;
import com.example.myfestival_sms.Fragment.SmsHistoryFragment;
import com.example.myfestival_sms.R;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    private String[] mTitle = {"节假日列表", "短信记录"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mTableLayout = (TabLayout) findViewById(R.id.title_tablyout);
        mViewPager = (ViewPager) findViewById(R.id.context_viewpage);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 1)
                    return new SmsHistoryFragment();
                return new FestivalCategoryFragment();
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position];
            }
        });
        //绑定viewpage
        mTableLayout.setupWithViewPager(mViewPager);
    }


}
