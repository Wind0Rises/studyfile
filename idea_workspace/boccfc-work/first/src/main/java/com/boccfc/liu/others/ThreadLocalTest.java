package com.boccfc.liu.others;

public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("asdfasd");
        threadLocal.set("addd");
        System.out.println(threadLocal.get());
    }
}
