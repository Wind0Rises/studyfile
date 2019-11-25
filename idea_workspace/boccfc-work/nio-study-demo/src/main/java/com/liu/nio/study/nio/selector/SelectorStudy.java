package com.liu.nio.study.nio.selector;

import sun.nio.ch.FileChannelImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 *
 *  Selector：SelectableChannel对象的多路复用器。【FileChannel没有继承或者实现SelectableChannel对象。】
 *
 *      可以通过自己的open()方法创建一个Selector对象，是通过系统默认的selector provider(selector提供者)创建的。
 *      SelectionKey对象表示每一个注册到Selector的SelectorChannel。
 *          每个Selector维护3个集合：
 *              * key集合：表示注册到该Selector的Channel，通过Channel的register方法可以往key集合中添加元素，
 *                        取消的key在selection操作之间从key集合中移除，key集合不能直接修改，通过keys方法返回。
 *              * selected-key集合：在selection操作中，从所有的key集合中识别出已经ready的key对应的Channel。
 *                        通过执行set的remove可以移除key或者执行迭代器对象的remove。否则key将不能通过其他方式移除。
 *                        不可以直接增加到selected-key集合中。
 *
 *              * cancelled-key集合：key已经被取消，但是对应的Channel还没有撤销，这个集合不可以直接访问，这个cancelled-key
 *                        总是key集合的子集。当key被取消，close对应的Channel或执行它的cancel方法，则添加key到cancelled-key
 *                        集合中。取消key将导致下一次Selection操作时它的通道被撤销，同时将从所有的Selector的key集合中删除。
 *
 *
 *
 *        open()：创建Selector对象
 *        isOpen()：检测Selector是否打开
 *        provider()：返回创建该Selector的Provider
 *        keys()：返回Key集合，key集合不能被直接修改，只有在被cancel和channel被撤销的时候key才被移除。并且不是线程安全的集合。
 *        selectedKeys()：返回selected-key集合，key可以直接移除，但是不可以直接增加。并且不是线程安全的集合。
 *        selectNow()：选择channel有IO事件的key。该方法是非阻塞的selection操作，如果自上次selection操作之后无channel具有IO事件，该方法会立刻返回零。执行该方法会立刻清除之前执行的wakeup影响。
 *        select(long timeout)：阻塞操作，只有在以下的状态变化时：（1）至少有一个IO的channel（2）调用selector.wakeup方法（3）当前线程被interrupt（4）timeout时间到(毫秒)
 *        select()： 阻塞操作，返回条件与select(long timeout)类似
 *        wakeup()：唤醒当前select阻塞的操作：如果另一个线程当前阻塞在select或select(long)方法。 如果当前没有select阻塞，则下次执行select或select(long)则直接返回，除非selectNow同时执行；
 *                  之后select和select(long)方法会正常阻塞；如果在select操作之间多次调用wakeup与调用一次效果是一样的。
 *        close()：关闭Selector。调用close方法，如果当前阻塞在selection操作，就像调用wakeup方法一样会立刻中断操作
 *                 与该selector关联的未cancelled的key将失效，它们的channel将撤销，与Selector相关的其他资源将释放。
 *                 如果Selector已经关闭，执行这个方法将没有影响。selector关闭之后，如果执行与selector相关的操作会报ClosedSelectorException。
 *
 *
 * SelectableChannel：
 *        SelectionKey：表示SelectableChannel在Selector中的注册的标记。这是一个通道。
 *        provider()：返回创建这个Channel的提供者provider。
 *        isRegistered()：判断当前Channel是否已经注册了Selector。
 *        validOps()：
 *        keyFor(Selector sel)：
 *        register(Selector selector, ...)：把给定的Selector注册到Channel上。
 *        configureBlocking(boolean block)：设置Channel的堵塞模式。
 *        isBlocking()：判断该Channel是否是堵塞模式。
 *
 */
public class SelectorStudy {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        System.out.println(socketChannel.isRegistered());

        Selector selector = Selector.open();
        socketChannel.register(selector, socketChannel.validOps());
        System.out.println(socketChannel.isRegistered());
    }

    class Server implements Runnable{

        @Override
        public void run() {

        }
    }
}