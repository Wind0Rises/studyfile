# Synchronized与Volitile

## 一、Synchronized模块

### 1.1 Synchronized的使用

* 修饰实例方法：对当前实例对象this加锁。实例如下图：

```java
public class SynchronizedDemo {
    /**
     *
     */
    public synchronized void lock() {

    }
}
```

* 修饰静态方法：对当前类的Class对象加锁。实例如下图：

```java
public class SynchronizedDemo {
    /**
     *
     */
    public static synchronized void lock() {

    }
}
```

* 修饰代码块—指定对象加锁：

```java
public class SynchronizedDemo {

    public static Object lockObject = new Object();

    /**
     * 对指定对象进行加锁。
     */
    public static void specialOjbectLock() {
        syschronized (lockObject) {
            // ...具体代码
        }
    }

    /**
     * 对Class对象进行加锁。
     */
    public static void classOjbectLock() {
        syschronized (SynchronizedDemo.class) {
            // ...具体代码
        }
    }


}
```



### 1.2 Synchronized的原理

#### 1.2.1 Java对象头内容

java对象头由三个部分组成：Mark Word、存储着到对象类型数据的指针、数组长度（只有数组对象才有）。

Mark Work：默认存储对象的HashCode、分代年龄和锁标记为。

<center>32位Mark Work</center>

<img src="..\zzz_image\concurrent\32位Mark Work.png" alt="32位Mark Work" style="zoom:200%;" />

<center>64位Mark Work</center>

<img src="..\zzz_image\concurrent\64位Mark Work.png" alt="64位Mark Work" style="zoom:200%;" />



#### 1.2.2 Synchronized与monitorenter、monitorexit指令

monitorenter指令是在编译后插入到同步代码块的开始位置，而monitorexit是插入到方法结束处和异常处，JVM要保证每个monitorenter必须有对应的monitorexit与之配对。任何对象都有一个monitor与之关联，当且一个monitor被持有后，它将处于锁定状态。线程执行到monitorenter指令时，将会尝试获取对象所对应的monitor的所有权，即尝试获得对象的锁。

## 二、Volitile模块





## 三、引用

### 3.1 Synchronized

* https://dalin.blog.csdn.net/article/details/110020462
* https://blog.csdn.net/chixi3536/article/details/100609573
* https://www.freesion.com/article/89921088434/
* https://www.zhihu.com/question/55075763
