package com.boccfc.liu.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @desc    原子类操作与CAS算法。
 * @author Liuweian
 * @createTime 2019/11/9 11:17
 * @version 1.0.0
 */
public class AtomicTest {

    public static void main(String[] args) {
        UnsafeTest unsafeTest = new UnsafeTest();
        unsafeTest.setAge(12);
        System.out.println(unsafeTest.getIntVolatile());
    }

    static class UnsafeTest {
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

}
