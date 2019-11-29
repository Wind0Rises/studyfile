package com.boccfc.liu.others;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    public static void main(String[] args) {
        int i = 0;
        try {
            for (;;) {
                i++;
                if (i != 5) {
                    try {
                        System.out.println(i);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("################################");
                    LockSupport.park();
                    System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                }
            }
        } finally {
            System.out.println("出来了！！");
        }
    }
}
