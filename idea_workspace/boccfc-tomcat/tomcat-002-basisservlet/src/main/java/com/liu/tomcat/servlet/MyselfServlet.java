package com.liu.tomcat.servlet;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

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
        PrintWriter writer = res.getWriter();
        String html = "http/1.1 200 ok\n" +"\n\n";
        writer.println(html);
        writer.println("MyselfServlet");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
