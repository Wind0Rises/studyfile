package com.liu.theory.aop.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @desc 调用的操作对象。
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2019/12/27 9:30
 */
public class TestInvocationHandler<T> implements InvocationHandler {

    /**
     * 被代理对象。
     */
    private T proxyObject;


    public TestInvocationHandler(T proxyObject) {
        this.proxyObject = proxyObject;
    }

    /**
     * 生成代理对象
     */
    public T newInstance() {
        // 打印生成的代理类。
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        return (T) Proxy.newProxyInstance(proxyObject.getClass().getClassLoader(), proxyObject.getClass().
                        getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object result = method.invoke(proxyObject, args);
        System.out.println("after");
        return result;
    }
}
