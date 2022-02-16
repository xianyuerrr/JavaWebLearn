package com.xianyue.service;

import com.xianyue.dao.Grade;
import com.xianyue.dao.GradeDao;
import com.xianyue.dao.GradeDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @auther xianyue
 * @date 2022/2/14 - 星期一 - 16:41
 **/
@WebServlet(name = "index", value = "/index")
public class IndexServlet extends ViewBaseServlet {
    private static GradeDao dao = new GradeDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Grade> list = dao.getGradeList();
        HttpSession session = req.getSession();
        session.setAttribute("GradeList", list);
        // 客户端重定向
        // resp.sendRedirect("index.html");
        // 服务器端转发
        // req.getRequestDispatcher("index.html").forward(req, resp);

        //此处的视图名称是 index
        //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
        //逻辑视图名称 ：   index
        //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
        //所以真实的视图名称是：      /       index       .html
        super.processTemplate("index", req, resp);
    }
}
