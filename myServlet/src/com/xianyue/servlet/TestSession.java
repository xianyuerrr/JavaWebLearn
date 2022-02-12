package com.xianyue.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @auther xianyue
 * @date 2022/2/8 - 星期二 - 14:03
 **/
public class TestSession extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        // 获取 Session，若获取不到则创建新的，效果和 req.getSession(true) 相同
        // req.getSession(true) 则是若没有 session，就返回 null，不创建新 session
        HttpSession session = req.getSession();
        System.out.println("Session ID : " + session.getId());

        /*
        session.isNew() -> 判断当前 session 是否是新的
        session.getId() -> 获取 sessionID
        session.getMaxInactiveInterval() -> session 的非激活间隔时长，默认 1,800 秒
        session.invalidate() -> 强制会话失效
        session.getCreatTime() -> 获取创建时间
        session.getLastAccessedTime() -> 获取最后访问时间
        */

    }
}
