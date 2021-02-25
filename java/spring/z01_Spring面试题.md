# Spring相关的面试题

## 一、Spring原理

### 1.1 Spring的启动流程

* 



### 1.2 Spring Bean的初始化流程

* SmartInstantiationAwareBeanPostProcessor#determineCandidateConstructors()确定候选构造函数，一般不会出现这样的Bean。
* 通过反射获取Bean Definition对应的实例，并包装成BeanWrapper。
* MergedBeanDefinitionPostProcessor#postProcessMergedBeanDefinition(....)合并Bean Definition。
* InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation(Class<?> beanClass, String beanName)实例化前的操作。
* 设置Bean的属性值。InstantiationAwareBeanPostProcessor#postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName)
* 调用各种Aware实例的方法（BeanNameAware、BeanClassLoaderAware、BeanFactoryAware）
* 调用BeanPostProcessor#postProcessBeforeInitialization(Object bean, String beanName)初始化前的操作。
* 如果Bean实现了InitializingBean类，调用InitializingBean#afterPropertiesSet()方法。
* 调用init-method方法。
* 调用BeanPostProcessor#postProcessAfterInitialization(Object bean, String beanName)初始化后的操作。



### 1.3 BeanFactory与FactoryBean的区别？

* BeanFactory是IoC底层容器。
* FactoryBean是创建Bean的一种方式，帮助实现复杂的初始化逻辑。



### 1.4 BeanFactory与ApplicationContext的联系

* BeanFactory提供了最简单的容器功能，是基本的IoC容器框架；ApplicationContext基础BeanFactory接口，提供了更加丰富的功能，比如：事件发布、国际化、资源管理等，ApplicationContext则提供了企业级IoC框架。



### 1.5 Spring事务 

## 二、Spring AOP原理



## 三、

