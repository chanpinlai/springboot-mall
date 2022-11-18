package com.jakgcc.springbootmall.constant;

public class MyTest {
    public static void main(String[] args) {
        //Enum convert String
        ProductCategory category = ProductCategory.FOOD;
        String s = category.name();

        //String convert Enum
        String s2 = "CAR";
        ProductCategory category2 = ProductCategory.valueOf(s2);
    }
}
