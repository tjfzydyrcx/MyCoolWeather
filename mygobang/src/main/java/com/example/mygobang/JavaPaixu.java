package com.example.mygobang;


/**
 * Created by Administrator on 2018-02-01 0001.
 */
public class JavaPaixu {
  static   int[] arr = {12, 10, 11, 7, 6, 5, 4, 3, 2, 1};

    public static void main(String[] args) {
        init();
        init1();
        maopao();
    }
    /**
     * 选择排序
     */
    public static void init() {
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }



        }
        for (int a = 0; a < arr.length; a++) {
            System.out.println("SHUJU==" + arr[a] + "");
        }
    }
    /**
     * 优化的
     * 选择排序
     */
    public static void init1() {
        int k;
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            k = i;
            for (int j = k + 1; j < arr.length; j++) {
                if (arr[k] > arr[j]) {
                    k = j;
                }
            }
            if (i != k) {
                temp = arr[i];
                arr[i] = arr[k];
                arr[k] = temp;
            }
        }
        for (int a = 0; a < arr.length; a++) {
            System.out.println("SHUJU222==" + arr[a] + "");
        }
    }

    public static void maopao() {
        boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++) {
            flag = false;
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
        for (int a = 0; a < arr.length; a++) {
            System.out.println("SHUJU111==" + arr[a] + "");
        }
    }
}
