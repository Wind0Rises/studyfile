package com.boccfc.liu.unsafe;

import com.boccfc.liu.atomic.AtomicTest;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @desc 
 * @author Liuweian
 * @createTime 2019/11/9 19:54
 * @version 1.0.0
 */
public class UnsafeTest {

    public static void main(String[] args) {
        BlockingTest blockingTest = new BlockingTest();
        new Thread(blockingTest, "Thread-A").start();
        new Thread(blockingTest, "Thread-B").start();
    }

    static class BlockingTest implements Runnable{
        Object object = new Object();

        @Override
        public void run() {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "   ###################");
                UnsafeTest.unsafe.park(true, System.currentTimeMillis() + 10000L);
            }
        }
    }

    private static final Unsafe unsafe;

    private static final long valueOffset;

    private int age;

    static {
        try {
            // 01、通过Unsafe.class获取theUnsafe字段。
            Field singleoneInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
            singleoneInstanceField.setAccessible(true);
            unsafe = (Unsafe)singleoneInstanceField.get(null);

            // 获取age在UnsafeTest类中的偏移量。
            valueOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("age"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    public int getIntVolatile() {
        return unsafe.getIntVolatile(this, valueOffset);
    }


    public long getOffset() {
        return valueOffset;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
