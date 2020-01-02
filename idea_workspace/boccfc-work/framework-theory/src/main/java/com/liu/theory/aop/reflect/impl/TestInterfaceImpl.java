package com.liu.theory.aop.reflect.impl;

import com.liu.theory.aop.reflect.ITestInterface;

/**
 * @desc 被代理接口的一个实现。
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2019/12/27 9:27
 */
public class TestInterfaceImpl implements ITestInterface {

    @Override
    public void testMethod(String parameter) {
        System.out.println("被代理接口的一个实现TestInterfaceImpl,传入的参数为：" + parameter);
    }
}
