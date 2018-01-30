package com.example.tjf.mycoolweather.Mygson;

import com.google.gson.annotations.SerializedName;

/**
 * 预测类
 * Created by Administrator on 2017-12-25 0025.
 */
public class Forecast {
    public String date;
    @SerializedName("tmp")
    public Temperature temperature;//温度
    @SerializedName("cond")
    public More more;

    public class Temperature {

        public String max;
        public String min;

    }

    public class More {
        @SerializedName("txt_d")
        public String info;
    }
}
