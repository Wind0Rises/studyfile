package com.boccfc.liu.collection;


import java.util.HashMap;

public class JDK8MapTest {
    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>(10);
        hashMap.put("liu", "weian");
        /**
         * 如果key已经存在了，不会进行任何操作。
         * 如何key不存在，新增一个数据，key为key,value为函数接口返回的值，如果返回null，不进行操作。
         */
        hashMap.computeIfAbsent("liude", key -> {
            System.out.println(key);
            return "liude";
        });
        System.out.println(hashMap);

        System.out.println("##############################");
        /**
         * 如果key不存在，不进行操作。
         * 如果key存在，用函数接口返回的值覆盖原有的值。如果函数接口返回的值为null，删除原有的key。
         */
        hashMap.computeIfPresent("liude", (key, value) -> {
            System.out.println(key);
            System.out.println(value);
            return "new_liude";
        });
        System.out.println(hashMap);
    }
}
