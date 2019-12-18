package com.liu.tomcat.http;

import com.liu.tomcat.Request;
import com.liu.tomcat.Response;
import com.liu.tomcat.processor.ServletProcessor;
import com.liu.tomcat.processor.StaticResourcesProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2019/12/18 16:31
 */
public class HttpProcessor {

    /**
     * 拦截servlet请求
     */
    public static final String SERVLET_URI = "/servlet/";

    private HttpConnector httpConnector;

    HttpProcessor(HttpConnector httpConnector) {
        this.httpConnector = httpConnector;
    }

    /**
     * 处理一个socket请求。
     * @param socket 一个请求。
     */
    public void process(Socket socket) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            // 01、从Socket获取输入输出流
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            // 02、创建请求、响应。
            Request request = new Request(inputStream);
            Response response = new Response(outputStream);
            response.setRequest(request);

            if (request.isSpecifyReq()) {
                socket.close();
                return;
            }

            // 03、根据不同的请求，去不同的处理方式。
            if (request.getUri().startsWith(SERVLET_URI)) {
                ServletProcessor servletProcessor = new ServletProcessor();
                servletProcessor.process(request, response);
            } else {
                StaticResourcesProcessor staticResourcesProcessor = new StaticResourcesProcessor();
                staticResourcesProcessor.process(request, response);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseRequest(InputStream inputStream, OutputStream outputStream) {

    }
}
