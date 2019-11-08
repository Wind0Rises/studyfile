package com.boccfc.liu.analysis;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.openjdk.jol.info.ClassLayout;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

public class BinObjectTest {

    static PublicObject publicObject = new PublicObject();

    public static void main(String[] args) throws IOException {
        SynchronizedTest synchronizedTest = new SynchronizedTest();
        /**
         * 如果没有调用hashCode，对象头就没有hashCode的记录
         */
        System.out.println(ClassLayout.parseInstance(publicObject).toPrintable());
        System.out.println(publicObject.hashCode());
        /**
         * 如果调用了hashCode，会把对象的hashCode加入到对象头中。
         */
        System.out.println(ClassLayout.parseInstance(publicObject).toPrintable());

        /**
         * 测试分为一个线程和两个线程。
         */
        new Thread(synchronizedTest).start();
        new Thread(synchronizedTest).start(); // 如果测试轻量级锁，注释本行。
    }

    /**
     * 用于测试锁的变化，
     *      无线程竞争时，并且是第一次进入，是轻量级锁。
     *      有多线程竞争时，是重量级锁。
     */
    static class SynchronizedTest implements Runnable{
        @Override
        public void run() {
            System.out.println(ClassLayout.parseInstance(publicObject).toPrintable());
            System.out.println("############################################");
            synchronized(publicObject) {
                try {
                    TimeUnit.SECONDS.sleep(2L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(ClassLayout.parseInstance(publicObject).toPrintable());
                try {
                    TimeUnit.SECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ###########################  下面是不相关的  #########################
    /**
     * 对象序列化。把java对象序列化为流。
     */
    public static void serializableTest() throws IOException {
        PublicObject publicObjectTest = new PublicObject();

        ByteOutputStream byteOutputStream = new ByteOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteOutputStream);
        oos.writeObject(publicObjectTest);
        byte[] bytes = byteOutputStream.getBytes();

        synchronized (publicObjectTest) {
            StringBuilder sb = new StringBuilder();
            for (byte item : bytes) {
                String transResult = String.format("%8s", Integer.toBinaryString(item & 0xFF)).replace(' ', '0');
                sb.append(transResult);
            }
            System.out.println(sb.toString());
        }
    }
}
