package com.boccfc.liu.proxy.jdk;

public class ProxyedInterfaceImpl implements ProxyedInterface {

    @Override
    public void invocationMethod() {
        System.out.println("被代理对象的方法。");
    }
}
