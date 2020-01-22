package com.liu.spring.boot.redis;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @desc
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2020/1/22 14:10
 */
public class ConcurrentHashMapTest {

    private static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        // ConcurrentHashMapTest test = new ConcurrentHashMapTest();
        // 0011
        // 1001 -- 1011
        System.out.println(3|9);

        for (int i = 0; i < 10; i++) {
            System.out.println("##########################################");
            new Thread(new PutElement("key" + i, "value" + i), "map-thread-" + i).start();
            System.out.println("#################" + i + "####################");
        }

    }

    static class PutElement implements Runnable {

        private String key;

        private String value;

        public PutElement(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            System.out.println("新增数据开始");
            map.put(key, value);
            System.out.println("新增数据开始");
        }

    }


    public static void specif() {
        int hashCode = new Object().hashCode();
        System.out.println(hashCode);
        System.out.println(hashCode ^ (hashCode >>> 16));
        System.out.println(-8 >> 2);


        System.out.println(-8 >>> 2);
        // 1000 0000 0000 0000 0000 1000  原码
        // 1111 1111 1111 1111 1111 0111  反码 -- 最高位不变，其他位取反
        // 1111 1111 1111 1111 1111 1000  补码 -- 反码 + 1
        // 0011 1111 1111 1111 1111 1110  结果
    }
}
