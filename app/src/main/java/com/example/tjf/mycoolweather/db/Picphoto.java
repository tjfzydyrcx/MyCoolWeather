package com.example.tjf.mycoolweather.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Administrator on 2018-06-02 0002.
 */

public class Picphoto extends LitePalSupport {
    private int id;
    private String PicName;//省名
    private byte[] Picfile;//省code

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicName() {
        return PicName;
    }

    public void setPicName(String picName) {
        PicName = picName;
    }

    public byte[] getPicfile() {
        return Picfile;
    }

    public void setPicfile(byte[] picfile) {
        Picfile = picfile;
    }
}
