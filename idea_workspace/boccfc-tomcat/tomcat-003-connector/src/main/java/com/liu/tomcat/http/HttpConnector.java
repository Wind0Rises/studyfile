package com.liu.tomcat.http;

import com.liu.tomcat.util.Constants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @desc 负责创建一个Socket，该Socket会等待http请求，
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2019/12/18 16:15
 */
public class HttpConnector implements Runnable {

    /**
     * 连接器的状态
     */
    private boolean stopped;

    /**
     * 协议
     */
    private String scheme = "http";

    /**
     * 开启一个Socket监听。
     */
    public void run() {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(Constants.SERVER_PORT, 1, InetAddress.getByName("127.0.0.1"));
            stopped = false;

            System.out.println("服务端启动成功，端口为" + Constants.SERVER_PORT + "....");
        } catch (IOException e) {
            System.out.println("启动服务异常，");
            e.printStackTrace();
            System.exit(1);
        }

        while (!stopped) {
            Socket socket = null;

            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("获取请求失败，重新进入等待....");
                continue;
            }

            HttpProcessor httpProcessor = new HttpProcessor(this);
            httpProcessor.process(socket);
        }
    }

    /**
     * 启动一个连接。
     */
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

}
