package com.example.myphotonine;

/**
 * Created by Administrator on 2018-05-18 0018.
 */

public abstract class AbsMapFactory  {
    public  abstract <T extends  IMapview> T createMapView(Class<T> clzz);
}
