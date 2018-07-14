package com.example.tjf.mycoolweather.Strategys;

/**
 * Created by Administrator on 2018-06-05 0005.
 */

public class CalPriceFactory {
    public CalPriceFactory() {
    }

   public  static Calprice createCalPrice(Player customer){
       if (customer.getTotalAmount() > 30000) {//3000则改为金牌会员计算方式
           return new Calprice.GoldVip();
       }else if (customer.getTotalAmount() > 20000) {//类似
           return new Calprice.SuperVip();
       }else if (customer.getTotalAmount() > 10000) {//类似
           return new Calprice.Vip();
       }else {
           return new Orgnic();
       }
   }


}
