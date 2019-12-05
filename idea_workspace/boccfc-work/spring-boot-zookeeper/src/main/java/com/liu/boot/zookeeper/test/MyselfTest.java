package com.liu.boot.zookeeper.test;

public class MyselfTest {

    public static void main(String[] args) {
        System.out.println(testFor());
    }

    public static int testFor() {
        for (int i = 1; i < 100; i++) {
            if (i % 2 == 0) {
                return i;
            }
        }
        return 0;
    }

}
