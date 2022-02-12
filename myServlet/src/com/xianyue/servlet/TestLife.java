package com.xianyue.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther xianyue
 * @date 2022/2/7 - 星期一 - 19:21
 **/
public class TestLife extends HttpServlet {
    public TestLife() {
        System.out.println("正在实例化......");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("正在初始化......");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        System.out.println("正在服务......");
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("正在销毁    ......");
    }
}
