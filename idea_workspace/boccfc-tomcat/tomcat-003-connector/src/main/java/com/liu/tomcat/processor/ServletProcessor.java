package com.liu.tomcat.processor;

import com.liu.tomcat.Request;
import com.liu.tomcat.Response;
import com.liu.tomcat.util.Constants;
import com.sun.org.apache.bcel.internal.classfile.ConstantString;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * @desc Servlet的处理。
 * @author Liuweian
 * @createTime 2019/12/17 23:23
 * @version 1.0.0
 */
public class ServletProcessor {

    private static final String SERVLET_PACKAGE = "com/liu/tomcat/servlet/";

    /**
     *  Servlet的具体处理。
     *  Servlet
     * @param request
     * @param response
     */
    public void process(Request request, Response response) {
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);

        // URLClassLoader：用于加载class和资源的。
        URLClassLoader urlClassLoader = null;

        try {
            URL[] urls = new URL[1];
            URLStreamHandler urlStreamHandler = null;

            // File.getCanonicalPath()：获取此抽象路径名的规范路径名字符串。
            File classPath = new File( Constants.WEB_ROOT + Constants.RESOURCES_PATH + File.separator + "class");
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();

            urls[0] = new URL(null, repository, urlStreamHandler);
            urlClassLoader = new URLClassLoader(urls);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class servletClass = null;
        try {
            servletClass = urlClassLoader.loadClass("com.liu.tomcat.servlet." + servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Servlet servlet = null;
        try {
            servlet = (Servlet) servletClass.newInstance();
            servlet.service(request, response);
        }  catch (IOException | ServletException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
