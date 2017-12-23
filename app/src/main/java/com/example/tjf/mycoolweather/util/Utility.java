package com.example.tjf.mycoolweather.util;

import android.text.TextUtils;

import com.example.tjf.mycoolweather.db.City;
import com.example.tjf.mycoolweather.db.County;
import com.example.tjf.mycoolweather.db.Province;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017-12-23 0023.
 */
public class Utility {
    /**
     * 解析和保存服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String string) {
        if (!TextUtils.isEmpty(string)) {
            try {
                JSONArray allProvince = new JSONArray(string);
                for (int i = 0; i < allProvince.length(); i++) {
                    JSONObject provinceobject = allProvince.getJSONObject(i);
                    Province mProvince = new Province();
                    mProvince.setProviceName(provinceobject.getString("name"));
                    mProvince.setProvinceCode(provinceobject.getInt("id"));
                    mProvince.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 解析和保存服务器返回的市级数据
     */
    public static boolean handleCityResponse(String string, int provinceId) {
        if (!TextUtils.isEmpty(string)) {
            try {
                JSONArray allCity = new JSONArray(string);
                for (int i = 0; i < allCity.length(); i++) {
                    JSONObject Cityobject = allCity.getJSONObject(i);
                    City mCity = new City();
                    mCity.setCityName(Cityobject.getString("name"));
                    mCity.setCityCode(Cityobject.getInt("id"));
                    mCity.setProvinceId(provinceId);
                    mCity.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和保存服务器返回的县级数据
     */
    public static boolean handleCountyResponse(String string, int cityId) {
        if (!TextUtils.isEmpty(string)) {
            try {
                JSONArray allCounty = new JSONArray(string);
                for (int i = 0; i < allCounty.length(); i++) {
                    JSONObject Countyobject = allCounty.getJSONObject(i);
                    County mCounty = new County();
                    mCounty.setCountyName(Countyobject.getString("name"));
                    mCounty.setWeatherId(Countyobject.getString("weather_id"));
                    mCounty.setCityId(cityId);
                    mCounty.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
