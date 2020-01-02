package com.liu.theory.aop.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

/**
 * @desc Cglib动态代理测试
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2019/12/30 10:38
 */
public class Test {

    public static void main(String[] args) {
        // 代理类class文件存入本地磁盘方便我们反编译查看源码
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code");

        // Enhancer [ɪnˈhænsər]：增强剂。
        // 通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();

        // 设置enhancer对象的父类
        enhancer.setSuperclass(ByProxyClass.class);

        // 设置enhancer的回调对象
        enhancer.setCallback(new ConsumeInterceptor());

        // 创建代理对象
        ByProxyClass proxy= (ByProxyClass) enhancer.create();

        System.out.println(proxy.getClass());

        // 通过代理对象调用目标方法
        proxy.byProxyMethod();
    }

}
