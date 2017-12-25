package com.example.tjf.mycoolweather.NetWorks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;


/**
 * Created by Administrator on 2017-12-25 0025.
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    private NetEvent netEvent;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWrok = NetUtil.getNetWorkState(context);
            if (netEvent != null) {
                netEvent.onNetChange(netWrok);
            }
        }
    }

    public void setNetEvent(NetEvent netEvent) {
        this.netEvent = netEvent;
    }
}
