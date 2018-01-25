package com.example.myfestival_sms.Bean;

/**
 * Created by Administrator on 2018-01-25 0025.
 */
public class Msg {

    private int id;
    private int festivalid;
    private String content;

    public Msg(int id, int festivalid, String content) {
        this.id = id;
        this.festivalid = festivalid;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFestivalid() {
        return festivalid;
    }

    public void setFestivalid(int festivalid) {
        this.festivalid = festivalid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "id=" + id +
                ", festivalid=" + festivalid +
                ", content='" + content + '\'' +
                '}';
    }
}
