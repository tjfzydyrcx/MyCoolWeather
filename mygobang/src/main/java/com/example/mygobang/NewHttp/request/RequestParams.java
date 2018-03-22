package com.example.mygobang.NewHttp.request;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018-03-01 0001.
 */

public class RequestParams {

    public ConcurrentHashMap <String ,String> urlParams=new ConcurrentHashMap<>();
    public ConcurrentHashMap<String,Objects> fileParams=new ConcurrentHashMap<>();

    public RequestParams(){

    }
}
