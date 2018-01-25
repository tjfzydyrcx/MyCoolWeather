package com.example.myfestival_sms.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-01-25 0025.
 */
public class FestivalLab {
    private static FestivalLab mInstance;
    private List<Festival> mFestivals = new ArrayList<Festival>();
    private List<Msg> mMsgs = new ArrayList<Msg>();
    private FestivalLab() {
        mFestivals.add(new Festival(1, "国庆节"));
        mFestivals.add(new Festival(2, "中秋节"));
        mFestivals.add(new Festival(3, "元旦"));
        mFestivals.add(new Festival(4, "春节"));
        mFestivals.add(new Festival(5, "端午节"));
        mFestivals.add(new Festival(6, "七夕节"));
        mFestivals.add(new Festival(7, "圣诞节"));
        mFestivals.add(new Festival(8, "儿童节"));
        mMsgs.add(new Msg(1, 1, "如果我有一百万我将送你999999，我有一百万吗没有所有我只能用一毛钱发个短信祝你国庆快乐。"));
        mMsgs.add(new Msg(2, 1, "可记得那年红旗飘扬，我们豪情万状，共为祖国发下宏愿，为此不惜一切。如今往事如烟，曾记否故"));
        mMsgs.add(new Msg(3, 1, "可记得那年红旗飘扬"));
        mMsgs.add(new Msg(4, 1, "共为祖国发下宏愿"));
        mMsgs.add(new Msg(5, 1, "为此不惜一切"));
        mMsgs.add(new Msg(6, 1, "天蓝蓝，草青青，国庆长假振人心。"));
        mMsgs.add(new Msg(7, 1, "三秀秀，水清清，携手爱人去旅行。"));
        mMsgs.add(new Msg(8, 1, "国庆长假振人心"));
        mMsgs.add(new Msg(9, 1, "携手爱人去旅行"));

    }

    public List<Festival> getmFestivals() {
        return new ArrayList<Festival>(mFestivals);
    }

    public Festival getFestivalByid(int id) {
        for (Festival festival : mFestivals) {
            if (festival.getId() == id) {
                return festival;
            }
        }
        return null;
    }


    public List<Msg> getMsgListByid(int id) {
        List<Msg> mMsg = new ArrayList<Msg>();
        for (Msg msg : mMsgs) {
            if (msg.getFestivalid() == id) {
                mMsg.add(msg);
            }
        }
        return mMsg;
    }

    public Msg getMsgByid(int festivalid,int id) {
        for (Msg msg : mMsgs) {
            if (msg.getFestivalid()==festivalid) {
                if (msg.getId() == id) {
                    return new Msg(msg.getId(), msg.getFestivalid(), msg.getContent());
                }
            }
        }
        return null;
    }

    public static FestivalLab getmInstance() {
        if (mInstance == null) {
            synchronized (FestivalLab.class) {
                if (mInstance == null) {
                    mInstance = new FestivalLab();
                }
            }
        }
        return mInstance;
    }
}
