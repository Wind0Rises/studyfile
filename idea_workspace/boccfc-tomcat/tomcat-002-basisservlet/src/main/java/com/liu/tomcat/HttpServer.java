package com.liu.tomcat;

import com.liu.tomcat.processor.ServletProcessor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端，监听给定端口，处理请求。
 */
public class HttpServer {

    private final static String PATH = "/tomcat-001-socket/src/main/resources";

    public static final String WEB_ROOT = System.getProperty("user.dir") + PATH + File.separator + "webroot";

    public static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    /**
     * 拦截servlet请求
     */
    public static final String SERVLET_URI = "/servlet/";

    public static final int SERVER_PORT = 8878;

    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;

        try {
            // 启动ServerSocket。
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("服务启动....");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!shutdown) {
            Socket socket = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                // 获取一个socket（获取一个请求）
                socket = serverSocket.accept();

                // 获取输入输出流
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                // 实例化一个请求，并解析socket的内容
                Request request = new Request(inputStream);
                request.parse();

                // 实例化一个响应，并发送一个响应。
                Response response = new Response(outputStream);
                response.setRequest(request);

                // 根据不同的请求，去不同的处理方式。
                if (request.getUri().startsWith(SERVLET_URI)) {
                    ServletProcessor servletProcessor = new ServletProcessor();
                    servletProcessor.process(request, response);
                } else {

                }

                socket.close();

                if (request.getUri() != null) {
                    shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
