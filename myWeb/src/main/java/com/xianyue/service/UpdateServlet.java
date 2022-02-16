package com.xianyue.service;

import com.xianyue.dao.Grade;
import com.xianyue.dao.GradeDao;
import com.xianyue.dao.GradeDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther xianyue
 * @date 2022/2/16 - 星期三 - 11:26
 **/
@WebServlet(name = "update", value = "/update")
public class UpdateServlet extends ViewBaseServlet{
    private static GradeDao dao = new GradeDaoImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String classCode = req.getParameter("classCode");
        Grade grade = null;
        if (classCode != null && classCode != "") {
            grade = dao.getGradeByClassCode(classCode);
        }
        req.setAttribute("grade", grade);
        super.processTemplate("update", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码
        req.setCharacterEncoding("utf-8");

        // 接受 post 数据
        String classCode = req.getParameter("classCode");
        String className = req.getParameter("className");
        Float credit = Float.valueOf(req.getParameter("credit"));
        String semester = req.getParameter("semester");
        Float usualGrades = Float.valueOf(req.getParameter("usualGrades"));
        Float finalGrades = Float.valueOf(req.getParameter("finalGrades"));
        Float overallGrades = Float.valueOf(req.getParameter("overallGrades"));

        // 根据数据创建对象
        Grade grade = new Grade(classCode, className, credit, semester, usualGrades, finalGrades, overallGrades);

        // 进行处理，在这里是据此更新数据库
        dao.add(grade);

        // req.getRequestDispatcher("index").forward(req, resp);
        // resp.sendRedirect("index");
    }
}
