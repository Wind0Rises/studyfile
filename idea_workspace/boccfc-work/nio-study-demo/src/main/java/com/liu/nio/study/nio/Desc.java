package com.liu.nio.study.nio;

/**
 * Nio主要组成为：Buffer、Channel、Selector。
 * 只用在网络I/O才能使用Selector。所以SocketChannel，DatagramChannel等才能使用，这些channel都继承SelectableChannel接口。
 */
public class Desc {

    /**
     *                          Thread
     *                            /\
     *                            ||
     *                         Selector
     *                            /\
     *               -------------------------------
     *              ||            ||              ||
     *          Channel1        Channel2        Channel3
     *
     */

    /**
     * 数据总是在Buffer和Channel之间进行交换。
     */
}
