package com.liu.nio.study.nio.channel;

import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Channel：I/O操作的纽带。通道表示与诸如硬件设备，文件，网络套接字或程序组件之类的实体的开放连接，
 *         该实体能够执行一个或多个不同的I / O操作（例如，读取或写入）。
 *
 *  isOpen()：channel是否是打开状态。
 *
 */
public class ChannelStudy {
    public static void main(String[] args) {
        channelStudy();
    }

    /**
     *
     */
    public static void channelStudy() {
        SocketChannel socketChannel = null;
        FileChannel fileChannel = null;
    }
}
