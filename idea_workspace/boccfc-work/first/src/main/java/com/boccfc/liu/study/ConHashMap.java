package com.boccfc.liu.study;

import java.util.concurrent.ConcurrentHashMap;

public class ConHashMap {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>(64);

        concurrentHashMap.put("zhang", "san");


        concurrentHashMap.get("zhang");
    }

}
