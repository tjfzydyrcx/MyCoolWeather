package com.example.tjf.mycoolweather.handler;

/**
 * Created by Administrator on 2018-07-16 0016.
 */

public class MyMessage {
    Handler target;
    public int what;
    public Object obj;

    @Override
    public String toString() {
      return   obj.toString();
    }
}
