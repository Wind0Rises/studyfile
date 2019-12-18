package com.liu.tomcat.start;

import com.liu.tomcat.http.HttpConnector;

/**
 * @author Liuweian
 * @version 1.0.0
 * @desc  启动类。
 * @createTime 2019/12/18 16:14
 */
public class Bootstrap {

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }

}
