package com.xianyue.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @auther xianyue
 * @date 2022/1/17 - 星期一 - 12:29
 **/
public class HelloServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
