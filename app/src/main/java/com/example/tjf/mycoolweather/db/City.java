package com.example.tjf.mycoolweather.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Administrator on 2017-12-23 0023.
 */
public class City extends LitePalSupport {

    private int id;
    private int provinceId;//所属省id
    private String cityName;//城市名
    private int cityCode;//城市code

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
