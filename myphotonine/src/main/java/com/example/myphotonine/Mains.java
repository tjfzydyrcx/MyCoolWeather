package com.example.myphotonine;

/**
 * Created by Administrator on 2018-05-18 0018.
 */

public class Mains {
    public static void main(String[] args) {
        AbsMapFactory mapFactory = MapFactory.getGetInstance();
        BaiduMapView baiduMapView = mapFactory.createMapView(BaiduMapView.class);
        baiduMapView.start();
    }
}
