package com.boccfc.liu.lock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 测试读写锁。
 */
public class TestReadWriteLock {

    class ProcessObject {
        private Object object;

        public ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public void get() throws InterruptedException {
            // 读上锁。
            readWriteLock.readLock().lock();

            try {
                System.out.println(Thread.currentThread().getName() + ": 准备读取数据");
                // TimeUnit.SECONDS.sleep(new Random().nextInt(10));

                /**/
                //  testGetWriteLock()测试使用。
                    TimeUnit.SECONDS.sleep(100);

                System.out.println(Thread.currentThread().getName() + ": 读取数据为" + this.object);
            } finally {
                // 读释放锁
                readWriteLock.readLock().unlock();
            }
        }

        public void put(Object object) throws InterruptedException {
            System.out.println("dddddddddddddddddddddddddddddddddddd");
            // 写上锁
            readWriteLock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + ": 准备写数据");
                // TimeUnit.SECONDS.sleep(new Random().nextInt(10));

                /*
                    testGetWriteLock()测试使用。
                    TimeUnit.SECONDS.sleep(10);
                */

                /*
                    // multiWriteLock()测试使用。
                    TimeUnit.SECONDS.sleep(3);
                */

                TimeUnit.SECONDS.sleep(100);

                this.object = object;
                System.out.println(Thread.currentThread().getName() + ": 写数据完成，现在的值" + this.object);
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }

        public void put(Object object, long timeOut) throws InterruptedException {
            System.out.println("dddddddddddddddddddddddddddddddddddd");
            // 写上锁
            readWriteLock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + ": 准备写数据");
                // TimeUnit.SECONDS.sleep(new Random().nextInt(10));

                /*
                    testGetWriteLock()测试使用。
                    TimeUnit.SECONDS.sleep(10);
                */

                /*
                    // multiWriteLock()测试使用。
                    TimeUnit.SECONDS.sleep(3);
                */

                TimeUnit.SECONDS.sleep(timeOut);

                this.object = object;
                System.out.println(Thread.currentThread().getName() + ": 写数据完成，现在的值" + this.object);
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }

        public ReadWriteLock getReadWriteLock() {
            return readWriteLock;
        }
    }


    public static void main(String[] args) throws Exception {
        // testGetWriteLock();
        // testReadLock();
        // multiReadLock();
        // multiWriteLock();
        test2();
    }

    public static void test2() throws Exception {
        final ProcessObject processObject = new TestReadWriteLock().new ProcessObject();
        Thread writeLock1 = new Thread(() -> {
            try {
                processObject.put(1000, 30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"writeLock1");
        Thread writeLock2 = new Thread(() -> {
            try {
                processObject.put(2000, 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"writeLock2");
        Thread readLock1 = new Thread(() -> {
            try {
                processObject.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"readLock1");
        Thread readLock2 = new Thread(() -> {
            try {
                processObject.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"readLock2");
        Thread readLock3 = new Thread(() -> {
            try {
                processObject.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"readLock3");

        writeLock1.start();
        TimeUnit.SECONDS.sleep(1);
        writeLock2.start();
        TimeUnit.SECONDS.sleep(1);

        readLock1.start();
        TimeUnit.SECONDS.sleep(1);
        readLock2.start();
        TimeUnit.SECONDS.sleep(1);
        readLock3.start();
        TimeUnit.SECONDS.sleep(1);


        TimeUnit.SECONDS.sleep(5000);

    }

    /**
     * 多个写锁
     *  当一个写获取到锁以后，如果没有释放锁，其他写在过来获取锁时无法获取到锁的。
     */
    public static void multiWriteLock() {
        final ProcessObject processObject = new TestReadWriteLock().new ProcessObject();
        Thread writeThread1 = new Thread(() -> {
            try {
                processObject.put(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"writeThread1");

        Thread writeThread2 = new Thread(() -> {
            try {
                processObject.put(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"writeThread2");

        writeThread1.start();
        writeThread2.start();
    }

    /**
     * 多个读锁：
     *      当一个读锁获取锁以后，但是没有释放锁，这个时候是有其他读操作可以继续过来获取锁。
     */
    public static void multiReadLock() {
        final ProcessObject processObject = new TestReadWriteLock().new ProcessObject();

        Thread readThread1 = new Thread(() -> {
            try {
                processObject.get();
                System.out.println("读线程操作完成。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"readThread1");

        Thread readThread2 = new Thread(() -> {
            try {
                processObject.get();
                System.out.println("读线程操作完成。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"readThread2");


        readThread1.start();
        readThread2.start();
    }

    /**
     * 获得写锁以后在获取读锁：
     *      当写获取锁的时候，是无法获取读锁的。读操作将被堵塞住，直到写释放锁。
     */
    public static void testGetWriteLock() {
        final ProcessObject processObject = new TestReadWriteLock().new ProcessObject();
        Thread writeThread = new Thread(() -> {
            try {
                processObject.put(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"writeThread");

        Thread readThread = new Thread(() -> {
            try {
                processObject.get();
                System.out.println("读线程操作完成。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"readThread");

        writeThread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readThread.start();
    }

    /**
     * 获得读锁以后在获取写锁：
     *      当读获取读锁时候，如果还没有释放读锁时，写操作将被堵塞住，直到读释放锁。
     */
    public static void testReadLock() {
        final ProcessObject processObject = new TestReadWriteLock().new ProcessObject();
        Thread writeThread = new Thread(() -> {
            try {
                processObject.put(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"writeThread");

        Thread readThread = new Thread(() -> {
            try {
                processObject.get();
                System.out.println("读线程操作完成。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"readThread");

        readThread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writeThread.start();
    }

    public static void test1() {
        final ProcessObject processObject = new TestReadWriteLock().new ProcessObject();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 3; j++) {
                        try {
                            processObject.put(new Random().nextInt(1000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        for (int i = 0; i < 3; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 3; j++) {
                        try {
                            // 多个线程读取操作
                            processObject.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        executorService.shutdown();
    }
}
