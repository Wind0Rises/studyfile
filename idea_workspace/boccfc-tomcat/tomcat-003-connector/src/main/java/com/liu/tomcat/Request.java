package com.liu.tomcat;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.Principal;
import java.util.*;

/**
 * 把一个socket封装成一个request。
 * 从socket的inputStream中获取对应的信息。
 * @author Liuweian
 */
public class Request implements HttpServletRequest {

    protected Map<String, String> headers = new HashMap<>(64);

    protected ArrayList<String> cookies = new ArrayList<>();

    protected Map<String, String> parameters = new HashMap<>(64);

    private InputStream inputStream;

    private String uri;

    private String method;

    private String scheme;

    private boolean specifyReq;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
        specifyReq = false;
        parse();
    }

    /**
     * 解析Socket的InputStream。
     */
    private void parse() {
        StringBuffer requestString = new StringBuffer();

        try {
            // 读取数据。
            int available = inputStream.available();

            if(available == 0) {
                specifyReq = true;
                return;
            }

            byte[] buffer = new byte[available];

            int readLength = 0;
            int readResult;

            // 如果第一次buffer没有读满，第二次进来就会一直卡着。
            while((readResult = inputStream.read(buffer)) != -1) {
                // 保存读取的数据。
                String readString = new String(buffer, 0, readResult);
                requestString.append(readString);

                // 判断下一步。
                readLength += readResult;
                if (readLength < available) {
                    continue;
                } else if (readLength == available) {
                    break;
                } else {
                    available = inputStream.available();
                    if (available <= readLength) {
                        break;
                    }
                }
            }

            if (inputStream != null) {
                inputStream.close();
            }

        } catch (IOException e) {
            System.out.println("#####################");
            e.printStackTrace();
        }

        System.out.println("【服务端】获取的内容: " );
        System.out.println(requestString.toString());


        parseHeaderLine(requestString.toString());
        System.out.println("【服务端】方法类型： " + method + "；请求的URI:" + uri + ";协议：" + scheme);

        parseHeader(requestString.toString());
    }


    /**
     * 解析请求行
     * @param requestString
     */
    private void parseHeaderLine(String requestString) {
        String headerLine = requestString.substring(0, requestString.indexOf(System.getProperty("line.separator")));
        String[] headerLineParameter = headerLine.split(" ");
        method = headerLineParameter[0];
        uri = headerLineParameter[1];
        scheme = headerLineParameter[2];
    }

    /**
     * 解析请求头。
     * @param processString
     */
    private void parseHeader(String processString) {
        String[] result = processString.split(System.getProperty("line.separator"));

        for (int i = 0; i < result.length; i++) {
            if (i == 0) {
                continue;
            }

            String header = result[i];

            if (header != null) {
                String[] preHeader = header.split(":");
                headers.put(preHeader[0].trim(), preHeader[1].trim());
            }
        }
    }

    /**
     * 获取URI。
     */
    public String getUri() {
        return uri;
    }

    public boolean isSpecifyReq() {
        return specifyReq;
    }

    public void setSpecifyReq(boolean specifyReq) {
        this.specifyReq = specifyReq;
    }

    @Override
    public Object getAttribute(String name) {
        return headers.get(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(headers.keySet());
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public long getContentLengthLong() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return (ServletInputStream) inputStream;
    }

    @Override
    public String getParameter(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String name, Object o) {

    }

    @Override
    public void removeAttribute(String name) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    @Override
    public String getRealPath(String path) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }

    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String name) {
        return 0;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return null;
    }

    @Override
    public int getIntHeader(String name) {
        return 0;
    }

    @Override
    public HttpServletMapping getHttpServletMapping() {
        return null;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public PushBuilder newPushBuilder() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    public String getRequestURI() {
        return null;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession(boolean create) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public String changeSessionId() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        return false;
    }

    @Override
    public void login(String username, String password) throws ServletException {

    }

    @Override
    public void logout() throws ServletException {

    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return null;
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        return null;
    }

    @Override
    public Map<String, String> getTrailerFields() {
        return null;
    }

    @Override
    public boolean isTrailerFieldsReady() {
        return false;
    }
}
