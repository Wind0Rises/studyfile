package com.boccfc.liu.collection;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueTest {

    public static void main(String[] args) {
        basisTest();
    }

    public static void basisTest() {
        LinkedBlockingQueue<String> lbq = new LinkedBlockingQueue<>();
        lbq.add("asd");
        System.out.println(lbq);
    }


}
