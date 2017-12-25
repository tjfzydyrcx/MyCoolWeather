package com.example.tjf.mycoolweather;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.tjf.mycoolweather.NetWorks.NetBroadcastReceiver;
import com.example.tjf.mycoolweather.NetWorks.NetEvent;
import com.example.tjf.mycoolweather.util.LogUtils;
import com.example.tjf.mycoolweather.util.ToastUtil;

/**
 * Created by Administrator on 2017-12-25 0025.
 */
public class BaseActivity extends AppCompatActivity implements NetEvent {
    /**
     * 网络状态
     */
    public int netMobile;

    /**
     * 这里保存一个值用来判断网络是否经历了由断开到连接
     */
    public boolean isNetChanges = false;
    /**
     * 监控网络的广播
     */
    private NetBroadcastReceiver netBroadcastReceiver;

    @Override
    protected void onStart() {
        super.onStart();
        if (netBroadcastReceiver == null) {
            netBroadcastReceiver = new NetBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netBroadcastReceiver, filter);
            netBroadcastReceiver.setNetEvent(this);
        }
    }

    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    public void isNetConnect() {
        switch (netMobile) {
            case 1:
                isNetChanges = true;
                LogUtils.e("\n当前网络类型:wifi");
                break;
            case 0:
                isNetChanges = true;
                LogUtils.e("\n当前网络类型:移动数据");
                break;
            case -1:
                isNetChanges = false;
                break;
            default:
                break;
        }
        if (isNetChanges == true) {
            ToastUtil.showLong(this, "网络连接");
        } else {
            ToastUtil.showLong(this, "无网络连接");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (netBroadcastReceiver != null) {
            unregisterReceiver(netBroadcastReceiver);
        }
    }
}
