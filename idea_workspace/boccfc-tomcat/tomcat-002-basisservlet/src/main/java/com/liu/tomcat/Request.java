package com.liu.tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 把一个socket封装成一个request。
 * 从socket的inputStream中获取对应的信息。
 */
public class Request {

    private Socket socket;

    private String uri;

    public Request(Socket socket) {
        this.socket = socket;
    }

    /**
     * 解析Socket的InputStream。
     */
    public void parse() {
        BufferedReader reader = null;
        StringBuffer request = new StringBuffer();
        byte[] buffer = new byte[2048];
        try {
            InputStream inputStream = this.socket.getInputStream();

            // 读取数据。
            int ch = -1;
            while ((ch = inputStream.read(buffer)) != -1) {
                String result = new String(buffer, 0, ch, "UTF-8");
                request.append(result);
                if (ch < 2048) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("#####################");
            e.printStackTrace();
        }

        System.out.println("【服务端】获取的内容: " + request.toString());
        uri = parseUri(request);
        System.out.println("【服务端】请求的URI: " + uri);
    }

    /**
     * 从socket的inputStream读取的内容中获取URI。
     */
    private String parseUri(StringBuffer request) {
        int index1;
        int index2;

        index1 = request.indexOf(" ");
        if (index1 != -1) {
            index2 = request.indexOf(" ", index1 + 1);

            if (index2 > index1) {
                return request.substring(index1 + 1, index2);
            }
        }

        return null;
    }

    /**
     * 获取URI。
     */
    public String getUri() {
        return uri;
    }
}
