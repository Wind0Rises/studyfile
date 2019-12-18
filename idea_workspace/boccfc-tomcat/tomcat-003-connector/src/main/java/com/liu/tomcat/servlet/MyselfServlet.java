package com.liu.tomcat.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Liuweian
 * @version 1.0.0
 * @desc
 * @createTime 2019/12/18 14:34
 */
public class MyselfServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Myself Servlet...init");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("##########################  Myself Servlet...service  #########################");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
