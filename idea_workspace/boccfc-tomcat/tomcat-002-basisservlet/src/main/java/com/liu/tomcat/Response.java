package com.liu.tomcat;

import java.io.*;
import java.net.Socket;

/**
 * 把一个socket的OutputStream封装成一个Response对应。
 */
public class Response {

    private Socket socket;

    private Request request;


    Response(Socket socket) {
        this.socket = socket;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * 向OutputStream对象中写入数据，
     */
    public void sendStaticResource() {
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        PrintWriter writer = null;

        try {
            if (request.getUri() != null) {
                File file = new File(HttpServer.WEB_ROOT, request.getUri());
                writer = new PrintWriter(this.socket.getOutputStream(), true);
                if (file.exists()) {
                    fis = new FileInputStream(file);
                    int ch = fis.read(buffer, 0, 1024);

                    if (!request.getUri().contains(".json")) {
                        String html = "http/1.1 200 ok\n" +"\n\n";
                        writer.println(html);
                    }

                    while (ch != -1) {
                        String readResult = new String(buffer, 0, ch, "UTF-8");
                        writer.println(readResult);
                        ch = fis.read(buffer, 0, 1024);
                    }
                } else {
                    String errorMsg = "You request file not find, Please check it.";
                    writer.write(errorMsg);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (writer != null) {
                writer.close();
            }
        }


    }
}
