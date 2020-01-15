package com.liu.shiro.basis.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;

/**
 * @desc
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2020/1/9 16:38
 */
@Component
public class ShiroFilterFactoryBeanTest implements BeanFactoryPostProcessor, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /*@Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof Filter) {
            System.out.println("Bean class: " + bean.getClass() + "  bean name: " + beanName);
        }

        if (beanName.equals("ShiroFilterFactoryBeanTest")) {
            System.out.println("Bean ++ class: " + bean.getClass() + "  bean name: " + beanName);
        }

        return bean;
    }*/


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("ddddddd " + beanFactory.getBean("&shiroFilter", BeanPostProcessor.class));

    }
}
