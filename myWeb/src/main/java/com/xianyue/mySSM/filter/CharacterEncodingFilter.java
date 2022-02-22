package com.xianyue.mySSM.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(urlPatterns = "*", initParams = {@WebInitParam(name = "encoding", value = "UTF-8")})
public class CharacterEncodingFilter implements Filter {
    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingStr = filterConfig.getInitParameter("encoding");
        if (encodingStr != null && "".equals(encodingStr)) {
            encoding = encodingStr;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
        // System.out.println("a");
    }

    @Override
    public void destroy() {

    }
}
