package com.example.myphotonine;

/**
 * Created by Administrator on 2018-05-18 0018.
 */

public class MapFactory extends AbsMapFactory {

    private static MapFactory getInstance;

    public MapFactory() {

    }

    public static MapFactory getGetInstance() {
        if (getInstance == null) {
            getInstance = new MapFactory();
        }
        return getInstance;
    }

    @Override
    public <T extends IMapview> T createMapView(Class<T> clzz) {
        try {
            return clzz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
