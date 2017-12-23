package com.example.tjf.mycoolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017-12-23 0023.
 */
public class County extends DataSupport {

    private int id;
    private String countyName;//县名
    private int cityId;//所属城市id
    private String weatherId;//天气id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }
}
