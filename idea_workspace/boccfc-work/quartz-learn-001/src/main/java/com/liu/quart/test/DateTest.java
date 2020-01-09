package com.liu.quart.test;

import java.util.Date;

/**
 * @desc
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2020/1/8 11:12
 */
public class DateTest {

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date.after(new Date(date.getTime() - 1000L)));
        System.out.println(date.compareTo(new Date(date.getTime() - 1000L)));
    }

}
