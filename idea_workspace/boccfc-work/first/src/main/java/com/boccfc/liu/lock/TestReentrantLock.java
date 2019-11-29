package com.boccfc.liu.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {

    /**
     * 默认是非公平锁。
     */
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    class Handler {
        private int parameter;

        public void set(int parameter) {
            try {
                reentrantLock.lock();

                System.out.println(Thread.currentThread().getName() + "  写操作开始。。。。");
                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.parameter = parameter;
                System.out.println(Thread.currentThread().getName() + "  写操作结束。。。。");
            } finally {
                reentrantLock.unlock();
            }
        }

        public void setWithTime(int parameter, long timeOut) {
            try {
                reentrantLock.lock();

                System.out.println(Thread.currentThread().getName() + "  写操作开始。。。。");
                try {
                    TimeUnit.SECONDS.sleep(timeOut);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.parameter = parameter;
                System.out.println(Thread.currentThread().getName() + "  写操作结束。。。。");
            } finally {
                reentrantLock.unlock();
            }
        }

        public int get(int parameter) {
            try {
                reentrantLock.lock();

                try {
                    TimeUnit.SECONDS.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return this.parameter;
            } finally {
                reentrantLock.unlock();
            }
        }
    }


    public static void main(String[] args) throws Exception {
        test2();
    }

    public static void test2() throws InterruptedException {
        Handler handler = new TestReentrantLock().new Handler();
        Thread thread1 = new Thread(() -> {
            handler.setWithTime(1, 30);
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            handler.setWithTime(1, 300);
        }, "thread2");

        thread1.start();
        TimeUnit.SECONDS.sleep(2);
        thread2.start();
        TimeUnit.SECONDS.sleep(3);

        System.out.println("锁的等待队列长度：" + reentrantLock.getQueueLength());
        System.out.println("thread1 --> 线程1的状态： " + thread1.getState());
        System.out.println("thread2 --> 线程2的状态： " + thread2.getState());


        System.out.println("####################################################################");
        TimeUnit.SECONDS.sleep(2);
        Thread thread3 = new Thread(() -> {
            handler.setWithTime(3, 300);
        }, "thread3");
        thread3.start();
        TimeUnit.SECONDS.sleep(2);

        System.out.println("锁的等待队列长度：" + reentrantLock.getQueueLength());
        System.out.println("thread1 --> 线程1的状态： " + thread1.getState());
        System.out.println("thread2 --> 线程2的状态： " + thread2.getState());
        System.out.println("thread3 --> 线程3的状态： " + thread2.getState());

        Thread thread4 = new Thread(() -> {
            handler.setWithTime(4, 300);
        }, "thread4");
        thread4.start();

        System.out.println("锁的等待队列长度：" + reentrantLock.getQueueLength());
        System.out.println("thread1 --> 线程1的状态： " + thread1.getState());
        System.out.println("thread2 --> 线程2的状态： " + thread2.getState());
        System.out.println("thread3 --> 线程3的状态： " + thread2.getState());
        System.out.println("thread4 --> 线程4的状态： " + thread2.getState());

        thread1.interrupt();

        System.out.println("锁的等待队列长度：" + reentrantLock.getQueueLength());
        System.out.println("thread1 --> 线程1的状态： " + thread1.getState());
        System.out.println("thread2 --> 线程2的状态： " + thread2.getState());
        System.out.println("thread3 --> 线程3的状态： " + thread2.getState());
        System.out.println("thread4 --> 线程4的状态： " + thread2.getState());
        TimeUnit.SECONDS.sleep(600);
    }

    /**
     * 多个线程同时获取锁。
     */
    public static void test1() {
        Handler handler = new TestReentrantLock().new Handler();
        Thread thread1 = new Thread(() -> {
            handler.set(1);
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            handler.set(2);
        }, "thread2");

        thread1.start();
        thread2.start();
    }
}
