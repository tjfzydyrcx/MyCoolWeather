package com.example.myfestival_sms.Bean;

import java.lang.ref.PhantomReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018-01-25 0025.
 */
public class SendMsg {

    private int id;
    private String msg;
    private String numbers;
    private String names;
    private String festivalName;
    private Date date;
    private String dateSt;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static final String TABLE_NAME = "tb_send_msg";
    public static final String COLUMN_NUMBER = "tb_send_numbers";
    public static final String COLUMN_NAME = "tb_send_names";
    public static final String COLUMN_FESTIVLANAME = "tb_send_festivalName";
    public static final String COLUMN_DATA = "tb_send_date";
    public static final String COLUMN_MSG = "tb_send_msg";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateSt() {
        dateSt = df.format(date);
        return dateSt;
    }


}
