package com.example.tjf.mycoolweather.HttpManager;

/**
 * Created by Administrator on 2018-03-23 0023.
 */

public class HttpManagers {
    public static IResquestManager getIResquestManager() {
//          return Xutils.getInstance();
        return OkHttputils.getmInstance();
    }
}
