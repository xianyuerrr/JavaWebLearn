package com.xianyue.service;

import com.xianyue.ConfigRead;
import com.xianyue.dao.Grade;
import com.xianyue.dao.GradeDao;
import com.xianyue.dao.GradeDaoImpl;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf8");
        String pageStr = req.getParameter("page");
        String keywords = req.getParameter("keyword") != null ? req.getParameter("keyword") : "";

        int page;
        int pageCount;

        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
            page = 1;
        }

        pageCount = dao.getGradeList().size() / ConfigRead.PageCnt + 1;

        req.setAttribute("page", page);


        List<Grade> list = dao.getGradeByClassName(keywords, page);

        HttpSession session = req.getSession();
        session.setAttribute("GradeList", list);
        session.setAttribute("pageCount", pageCount);
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
