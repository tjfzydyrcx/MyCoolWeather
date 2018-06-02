package com.example.mygobang;

public class BinarySearch  {

    public static void main(String[] args) {

        int array[] = new int[]{13, 18, 24, 35, 47, 50, 62, 83, 90, 115, 134};
        int low = 0;
        int high = array.length - 1;
        int mid = -1;
        int x = 90;
        while (low <= high) {
            mid = (low + high) / 2;
            System.out.println(mid+"==h3h");
            if (array[mid] == x) {
                System.out.println(x + "在数组中出现的位置" + mid);
                break;
            }
            if (array[mid] < x) {
                low = mid + 1;
                System.out.println(mid+"==h2h");
            }
            if (array[mid] > x) {
                high = mid - 1;
                System.out.println(mid+"==hh");
            }
            System.out.println("hh");
            if (low > high) {
                System.out.println("查找失败");
                break;
            }
        }
    }

}
