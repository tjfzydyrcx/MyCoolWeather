package com.example.mygobang.NewHttp.exception;

import java.util.Objects;

/**
 * Created by Administrator on 2018-03-01 0001.
 */

public class OkHttpException extends Exception {
    public static final long serialVersionUID=1L;
    private int ecode;
    private Objects emsg;

    public OkHttpException(int ecode, Objects emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Objects getEmsg() {
        return emsg;
    }
}
