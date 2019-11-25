package com.liu.nio.study.nio.channel;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Channel：I/O操作的纽带。通道表示与诸如硬件设备，文件，网络套接字或程序组件之类的实体的开放连接，
 *         该实体能够执行一个或多个不同的I / O操作（例如，读取或写入）。
 *         Channel：用于在字节缓冲区和位于通道另一侧的实体（通常是一个文件或套接字）之间有效地传输数据。
 *         通道被分为两大类:第一类是文件通道（FileChannel），另一种是套接字通道（SocketChannel、ServerSocketChannel、DatagramChannel）
 *
 *  ScatteringByteChannel、GatheringByteChannel
 *
 * Channels：工具类。
 *
 *
 *
 *  isOpen()：channel是否是打开状态。
 *
 *
 */
public class ChannelStudy {
    public static void main(String[] args) {
        System.out.println(1 << 0);
        System.out.println(1 << 2);
        System.out.println(1 << 3);
        System.out.println(1 << 4);
    }

    /**
     *
     */
    public static void channelStudy() {
        SocketChannel socketChannel = null;
        FileChannel fileChannel = null;
    }

    /**
     * SocketChannel:
     *      connect()：
     *
     *
     * SocketAddress是一个抽象类，里面啥都没有
     *      InetSocketAddress继承SocketAddress。
     */
    public static void socketChannelStudy() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        //Channels
    }
}
