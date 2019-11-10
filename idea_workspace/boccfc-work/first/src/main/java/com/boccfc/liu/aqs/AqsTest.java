package com.boccfc.liu.aqs;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @desc  AbstractQueueSynchronized测试类。
 * @author Liuweian
 * @createTime 2019/11/9 16:04
 * @version 1.0.0
 */
public class AqsTest {

    public static void main(String[] args) throws InterruptedException {
        // 可重入，同一个线程可以多次加锁。
        ReentrantLock lock = new ReentrantLock();
        new Thread(new LockTest(lock), "Lock-A").start();
        Thread.sleep(5000L);
        new Thread(new LockTest(lock), "Lock-B").start();
        Thread.sleep(5000L);
        new Thread(new LockTest(lock), "Lock-C").start();
    }

    static class LockTest implements Runnable{

        private ReentrantLock lock;

        LockTest() {

        }

        LockTest(ReentrantLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            lock.lock();
            lock.unlock();
        }
    }

}
