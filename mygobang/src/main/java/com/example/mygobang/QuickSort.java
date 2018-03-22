package com.example.mygobang;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

/**
 * 舍伍德算法优化快速排序
 *
 * @author Fairy2016
 */
public class QuickSort {

    //快速排序
    public static void sort(int a[], int low, int high) {
        if (low < high) {
            int base = Depart(a, low, high);
            //对基准左半边部分进行排序
            sort(a, low, base - 1);
            //对基准右半边部分进行排序
            sort(a, base + 1, high);
        }
    }

    //基准划分
    public static int Depart(int a[], int low, int high) {
        //舍伍德随机确定基准
        int d = (int) Math.random() * (high - low) + low;
        //交换默认基准与随机基准
        a[0] = a[d];
        a[d] = a[low];
        a[low] = a[0];
        while (low < high) {
            //从右向左扫描找比基准小的元素
            while (low < high && a[high] >= a[0])
                high--;
            a[low] = a[high];//赋值，更新基准位
            //从左向右扫描找比基准大的元素
            while (low < high && a[low] <= a[0])
                low++;
            a[high] = a[low];//赋值，更新基准位
        }
        //基准位最终位置已确定，是low或者high
        a[high] = a[0];
        return high;
    }

    public static void Print(int a[], int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print(a[i] + " ");
        }
    }

    public static void main(String args[]) throws IOException {
/*
        int n;
        int a[];
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            n = scanner.nextInt();
            if(n > 0) {
                a = new int[n+1];
                for(int i=1; i <= n; i++) {
                    a[i] = scanner.nextInt();
                }
                sort(a, 1, n);
                Print(a, n);
            }
        }
        scanner.close();
*/
        String url = "http://xjh.haitou.cc/wh/uni-7/after/hold/page-1/";
        Connection conn = Jsoup.connect(url);
        // 修改http包中的header,伪装成浏览器进行抓取
        conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
        Document doc = conn.get();

        // 获取tbody元素下的所有tr元素
        Elements elements = doc.select("tbody tr");
        System.out.println("shuju：" + elements);
        for (Element element : elements) {
            String companyName = element.select("div.text-success.company").text();
//            String companyName = element.select("div").text();
            String time = element.select("td.cxxt-holdtime").text();
            String address = element.select("td.text-ellipsis").text();
            System.out.println("公司：" + companyName);
            System.out.println("宣讲时间：" + time);
            System.out.println("宣讲学校：华中农业大学");
            System.out.println("具体地点：" + address);
            System.out.println("---------------------------------");
        }
    }
}
