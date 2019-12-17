package com.liu.tomcat.processor;

import com.liu.tomcat.Request;
import com.liu.tomcat.Response;

import java.io.IOException;

/**
 * @desc 静态资源的处理。
 * @author Liuweian
 * @createTime 2019/12/17 23:23
 * @version 1.0.0
 */
public class StaticResourcesProcessor {

    public void process(Request request, Response response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
