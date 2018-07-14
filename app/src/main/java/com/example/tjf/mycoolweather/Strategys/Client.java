package com.example.tjf.mycoolweather.Strategys;

/**
 * Created by Administrator on 2018-06-05 0005.
 */

public class Client {
    public static void main(String[] args) {
        Player player = new Player();
        player.buy(5000D);
        System.out.println("玩家需要付钱：" + player.calLastAmount());
        player.buy(12000D);
        System.out.println("玩家需要付钱：" + player.calLastAmount());
        player.buy(12000D);
        System.out.println("玩家需要付钱：" + player.calLastAmount());
        player.buy(12000D);
        System.out.println("玩家需要付钱：" + player.calLastAmount());
     /*   for(int i=1;i<=9;i++) { //控制行
            for(int j=1;j<=i;j++) {//控制列
                System.out.print(j+"×"+i+"="+i*j+"\t");// \t 跳到下一个TAB位置
            }
            System.out.println();
        }*/
//        print(8);


        //外层循环 每次打出一个*
      /*  for (int i = 1; i <=5; i++) {
            //填充空格
            for (int j = 1; j <= 5 - i; j++) {
                System.out.print(" ");
            }
            //内层循环 每次打印一个*
            for (int k = 1; k <= 5; k++) {
                System.out.print("*");
            }
            System.out.println();
        }*/

        circle(5);
    }

    public static void circle(int r) {
        for (int y = 0; y <= 2 * r; y += 2) {
            int x = (int) Math.round(r - Math.sqrt(2 * r * y - y * y));
            int len = 2 * (r - x);
            // 圆左的空白
            for (int i = 0; i <= x; i++) {
                System.out.print(' ');
            }
            // 左半圆
            System.out.print('*');
            // 中间空白
            for (int j = 0; j <= len; j++) {
                System.out.print(' ');
            }
            // 右半圆
            System.out.println('*');
        }
    }

    public static void print(int size) {
        if (size % 2 == 0) {
            size++; // 计算菱形大小
        }
        for (int i = 0; i < size / 2 + 1; i++) {
            for (int j = size / 2 + 1; j > i + 1; j--) {
                System.out.print(" "); // 输出左上角位置的空白
            }
            for (int j = 0; j < 2 * i + 1; j++) {
                System.out.print("*"); // 输出菱形上半部边缘
            }
            System.out.println(); // 换行
        }
        for (int i = size / 2 + 1; i < size; i++) {
            for (int j = 0; j < i - size / 2; j++) {
                System.out.print(" "); // 输出菱形左下角空白
            }
            for (int j = 0; j < 2 * size - 1 - 2 * i; j++) {
                System.out.print("*"); // 输出菱形下半部边缘
            }
            System.out.println(); // 换行
        }
    }
}
