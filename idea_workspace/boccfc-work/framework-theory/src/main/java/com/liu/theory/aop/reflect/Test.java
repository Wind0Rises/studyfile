package com.liu.theory.aop.reflect;

import com.liu.theory.aop.reflect.impl.TestInterfaceImpl;
import sun.misc.ProxyGenerator;

import java.io.*;
import java.lang.reflect.Proxy;

/**
 * @desc 测试
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2019/12/27 9:22
 */
public class Test {

    public static void main(String[] args) throws IOException {
        // 需要代理的对象。
        ITestInterface testInterface = new TestInterfaceImpl();

        // 方法调用类
        TestInvocationHandler<ITestInterface> invocationHandler = new TestInvocationHandler<>(testInterface);

        // 生成代理对象。
        ITestInterface productProxy = invocationHandler.newInstance();

        /**
         * System.out.println(productProxy);
         * 如果有上面一句，会调用一个TestInvocationHandler的invoke方法，
         * 因为这个默认调用println(*),如果*不为空，默认调用*的toString()方法。
         */



        // FileOutputStream fos = new FileOutputStream(new File("D://test//Proxy1.java"));
        // ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        // objectOutputStream.writeObject(productProxy.getClass());

        System.out.println(productProxy.getClass());


        // 调用生成的代理对象的对应方法。

        productProxy.testMethod("5");

        System.out.println(productProxy.getClass());
    }

}
