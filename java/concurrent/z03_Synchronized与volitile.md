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



### 1.3 偏向锁、轻量级锁存在的意义

​		JDK1.6为了减少获得锁和释放锁带来的性能消耗，引入了"偏向锁"和"轻量级锁"。在大多数情况下，锁不仅不存在多线程竞争，而且总是由同一个线程多次获得，为了让线程获得锁的代价更低而引入了偏向锁。当一个线程访问同步块并获取锁时，会在对象头和栈帧中的锁记录里存储锁偏向的线程ID，以后该线程在进入和退出同步块时不需要进行CAS操作来加锁和解锁，只需简单地测试一下对象头的Mark Word里是否存储着指向当前线程的偏向锁。如果测试成功，表示线程已经获得了锁。如果测试失败，则需要再测试一下Mark Word中偏向锁的标识是否设置成1（表示当前是偏向锁）：如果没有设置，则使用CAS竞争锁；如果设置了，则尝试使用CAS将对象头的偏向锁指向当前线程。

​		轻量级锁的目标是，减少无实际竞争情况下，使用重量级锁产生的性能消耗。顾名思义，轻量级锁是相对于重量级锁而言的。使用轻量级锁时，不需要申请互斥量，仅仅将Mark Word中的部分字节CAS更新指向线程栈中的Lock Record，如果更新成功，则轻量级锁获取成功，记录锁状态为轻量级锁；否则，说明已经有线程获得了轻量级锁，目前发生了锁竞争（不适合继续使用轻量级锁），接下来膨胀为重量级锁。



### 1.4 锁膨胀流程  <a href="https://blog.csdn.net/xueba8/article/details/88753443">好的博客</a>

![Synchronized锁膨胀的过程](..\zzz_image\concurrent\Synchronized锁膨胀的过程.png)



## 二、Volitile模块





## 三、引用

### 3.1 Synchronized

* https://dalin.blog.csdn.net/article/details/110020462
* https://blog.csdn.net/chixi3536/article/details/100609573
* https://www.freesion.com/article/89921088434/
* https://www.zhihu.com/question/55075763
