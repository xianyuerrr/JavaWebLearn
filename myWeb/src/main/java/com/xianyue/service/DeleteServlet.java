package com.xianyue.service;

import com.xianyue.dao.GradeDao;
import com.xianyue.dao.GradeDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther xianyue
 * @date 2022/2/16 - 星期三 - 15:35
 **/
@WebServlet(name = "delete", value = "/delete")
public class DeleteServlet extends ViewBaseServlet {
    private static GradeDao dao = new GradeDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String classCode = req.getParameter("classCode");
        if (classCode == null || classCode == "") {
            System.out.println("classCode 为空");
        }
        dao.delete(classCode);
    }
}
