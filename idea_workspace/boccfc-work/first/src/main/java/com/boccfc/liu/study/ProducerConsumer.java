package com.boccfc.liu.study;

import java.util.ArrayList;

/**
 * 生产者与消费者。
 */
public class ProducerConsumer {

    private static final int MAX_SIZE = 10;

    /**
     * 容器，共享变量
     */
    private static ArrayList<String> container = new ArrayList<String>(MAX_SIZE);


    /**
     * 生产者。
     */
    class Producer implements Runnable {

        public void run() {

        }
    }

    /**
     * 消费者。
     */
    class Consumer implements Runnable {

        public void run() {

        }
    }

}
