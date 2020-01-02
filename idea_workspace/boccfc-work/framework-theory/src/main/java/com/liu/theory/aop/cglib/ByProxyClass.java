package com.liu.theory.aop.cglib;

/**
 * @desc 被代理类。
 *          Cglib是无法处理final修饰的方法的。
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2019/12/30 10:40
 */
public class ByProxyClass {

    public ByProxyClass() {
        System.out.println("被代理类的构造函数。");
    }

    public void byProxyMethod() {
        System.out.println("被代理类中的方法。");
    }

}
