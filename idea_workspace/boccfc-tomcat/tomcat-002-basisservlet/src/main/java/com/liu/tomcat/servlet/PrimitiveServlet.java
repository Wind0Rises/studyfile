package com.liu.tomcat.servlet;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class PrimitiveServlet implements Servlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("PrimitiveServlet Servlet...init");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("##########################  PrimitiveServlet Servlet...service  #########################");
        PrintWriter writer = res.getWriter();
        String html = "http/1.1 200 ok\n" +"\n\n";
        writer.println(html);
        writer.println("PrimitiveServlet");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
