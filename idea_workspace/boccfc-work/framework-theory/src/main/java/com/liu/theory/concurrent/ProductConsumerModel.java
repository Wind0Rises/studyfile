package com.liu.theory.concurrent;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @desc
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2020/1/2 16:04
 */
public class ProductConsumerModel {

    private static ArrayList<Integer> data = new ArrayList<Integer>();

    private static Object mutex = new Object();

    public static void main(String[] args) {
        new Thread(new Product()).start();
        try {
            TimeUnit.SECONDS.sleep((long) new Random().nextInt(2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Consumer()).start();
    }


    static class Product implements Runnable {

        @Override
        public void run() {
            while(true) {
                synchronized (mutex) {
                    if (data.size() >=  10) {
                        try {
                            mutex.wait();
                        } catch (InterruptedException e) {
                        }
                    } else {
                        data.add(new Random().nextInt(1));
                        System.out.println("【添加】数据成功，数据大小为：" + data.size());
                        mutex.notifyAll();
                    }
                }

                try {
                    TimeUnit.SECONDS.sleep((long) new Random().nextInt(2));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (mutex) {
                    if (data.size() <= 0) {
                        try {
                            mutex.wait();
                        } catch (InterruptedException e) {

                        }
                    } else {
                        data.remove(0);
                        System.out.println("【移除】数据成功，数据大小为：" + data.size());
                        mutex.notifyAll();
                    }
                }

                try {
                    TimeUnit.SECONDS.sleep((long) new Random().nextInt(2));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
