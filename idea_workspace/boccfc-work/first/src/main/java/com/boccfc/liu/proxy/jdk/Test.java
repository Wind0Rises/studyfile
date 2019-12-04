package com.boccfc.liu.proxy.jdk;

import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args) {
        // 1、生成被代理对象。
        ProxyedInterface proxyedInterface = new ProxyedInterfaceImpl();

        // 2、生成代理对象。把被代理对象传入。
        ProxyClass proxyClass = new ProxyClass(proxyedInterface);

        System.out.println("####################" + proxyedInterface);
        ProxyedInterface proxyedInterface2 = (ProxyedInterface) Proxy.newProxyInstance(Test.class.getClassLoader(), new Class[]{ProxyedInterface.class}, proxyClass);
        System.out.println(proxyedInterface2);
        proxyedInterface2.invocationMethod();
    }

}
