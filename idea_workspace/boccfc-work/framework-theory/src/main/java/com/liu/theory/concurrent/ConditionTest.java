package com.liu.theory.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @desc
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2020/1/2 15:19
 */
public class ConditionTest {

    private static ReentrantLock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (i == 0) {
                new Thread(new Await(i)).start();
                continue;
            }

            if (i == 3) {
                new Thread(new Signal(i)).start();
                continue;
            }

            new Thread(new Other(i)).start();
        }
    }

    static class Await implements Runnable {
        int i;

        public Await(int d) {
            this.i = d;
        }

        @Override
        public void run() {
            System.out.println("-----------  await " + i);
            lock.lock();
            try {
                System.out.println("await 获取锁 " + i);
                TimeUnit.SECONDS.sleep(3);
                System.out.println("##########################  await 进入等待 " + i);
                condition.await();
                System.out.println("Await await " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class Signal implements Runnable {
        int i;

        public Signal(int d) {
            this.i = d;
        }

        @Override
        public void run() {
            System.out.println("-----------  signal " + i);
            lock.lock();
            try {
                System.out.println("Signal 获取锁 " + i);
                condition.signal();
                System.out.println("Signal signal " + i);
            } finally {
                lock.unlock();
            }
        }
    }

    static class Other implements Runnable {
        private int i;

        public Other(int d) {
            this.i = d;
        }

        @Override
        public void run() {
            System.out.println("-----------  other " + i);
            lock.lock();
            try {
                System.out.println("Other other " + i);
                if (i == 1) {
                    TimeUnit.SECONDS.sleep(5);
                } else {
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}