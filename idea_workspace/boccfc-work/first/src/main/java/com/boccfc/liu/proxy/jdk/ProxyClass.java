package com.boccfc.liu.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 实现InvacationHandler类。
 */
public class ProxyClass implements InvocationHandler {
    /**
     * 被代理对象。
     */
    private Object target;

    public ProxyClass() {

    }

    public ProxyClass(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("方法调用前。。。");
        Object object = method.invoke(target, args);
        System.out.println("方法调用后。。。");
        return object;
    }
}
