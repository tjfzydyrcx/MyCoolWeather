package com.example.tjf.mycoolweather.Strategys;

/**
 * Created by Administrator on 2018-06-05 0005.
 */

public class Player {

    private Double totalAmount = 0D;//消费总金额
    private Double amount = 0D;//单次消费金额
    private Calprice calprice = new Orgnic();//每个客户的初始计算规则，正常价格

    public void buy(Double amount) {

        this.amount = amount;
        totalAmount += amount;
      /*  if (totalAmount > 30000) {
            calprice = new Calprice.GoldVip();
        } else if (totalAmount > 20000) {
            calprice = new Calprice.SuperVip();
        } else if (totalAmount > 10000) {
            calprice = new Calprice.Vip();
        }*/
        calprice = CalPriceFactory.createCalPrice(this);
    }

    public Double calLastAmount() {
        return calprice.calprice(amount);
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}
