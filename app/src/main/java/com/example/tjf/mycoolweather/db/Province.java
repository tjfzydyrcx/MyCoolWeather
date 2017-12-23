package com.example.tjf.mycoolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017-12-23 0023.
 */
public class Province extends DataSupport {

    private int id;
    private String proviceName;//省名
    private int provinceCode;//省code



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProviceName() {
        return proviceName;
    }

    public void setProviceName(String proviceName) {
        this.proviceName = proviceName;
    }
    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }


}
