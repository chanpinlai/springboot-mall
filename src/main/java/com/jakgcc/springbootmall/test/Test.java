package com.jakgcc.springbootmall.test;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        int[] nums = { -1, 3, -10, -20 };
        System.out.println(nums[2]*nums[3]);
        ArrayList arrayList = new ArrayList();
        arrayList.add("d");
        arrayList.add("a");
        arrayList.add("c");
        arrayList.add("b");
        arrayList.set(1,"f");
        System.out.println(arrayList.toString());
    }
}
