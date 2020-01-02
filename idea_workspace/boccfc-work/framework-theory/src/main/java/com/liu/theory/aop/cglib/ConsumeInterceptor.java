package com.liu.theory.aop.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @desc 自定义方法拦截器
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2019/12/30 10:45
 */
public class ConsumeInterceptor implements MethodInterceptor {

    /**
     *
     * @param sub obj表示增强的对象，即实现这个接口类的一个对象
     * @param method method表示要被拦截的方法
     * @param args args表示要被拦截方法的参数
     * @param proxy proxy表示要触发父类的方法对象
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object sub, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        System.out.println("方法执行前");
        Object result = proxy.invokeSuper(sub, args);
        System.out.println("方法执行后");
        return result;
    }
}
