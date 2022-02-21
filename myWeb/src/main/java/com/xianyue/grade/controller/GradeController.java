package com.xianyue.grade.controller;

import com.xianyue.grade.dao.Grade;
import com.xianyue.grade.service.GradeService;
import com.xianyue.mySSM.ConfigRead;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @auther xianyue
 * @date 2022/2/17 - 星期四 - 20:14
 **/
public class GradeController {
    private static GradeService gradeService = null;

    protected String delete(HttpServletRequest req, HttpServletResponse resp) {
        String classCode = req.getParameter("classCode");
        if (classCode == null || "".equals(classCode)) {
            System.out.println("classCode 为空");
        }
        gradeService.delete(classCode);
        return "redirect:grade";
    }


    protected String add(HttpServletRequest req, HttpServletResponse resp) {
        String classCode = req.getParameter("classCode");
        Grade grade = null;
        if (classCode != null && classCode != "") {
            grade = gradeService.getGradeByClassCode(classCode);
        }
        req.setAttribute("grade", grade);
        return "processTemplate:update";
    }

    protected String update(HttpServletRequest req, HttpServletResponse resp) {
        // 接受 post 数据
        try {
            String classCode = req.getParameter("classCode");
            String className = req.getParameter("className");
            Float credit = Float.valueOf(req.getParameter("credit"));
            String semester = req.getParameter("semester");
            Float usualGrades = Float.valueOf(req.getParameter("usualGrades"));
            Float finalGrades = Float.valueOf(req.getParameter("finalGrades"));
            Float overallGrades = Float.valueOf(req.getParameter("overallGrades"));

            Grade grade = new Grade(classCode, className, credit, semester, usualGrades, finalGrades, overallGrades);

            // 进行处理，在这里是据此添加到数据库或者更新数据
            gradeService.add(grade);
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }

        // 根据数据创建对象
        // resp.sendRedirect("grade?operation=add");
        return "redirect:grade?operation=add";
    }



    protected String index(HttpServletRequest req, HttpServletResponse resp) {
        String pageStr = req.getParameter("page");
        String keywords = req.getParameter("keyword") != null ? req.getParameter("keyword") : "";

        int page;
        int pageCount;

        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
            page = 1;
        }

        pageCount = gradeService.getGradeList().size() / ConfigRead.PageCnt + 1;

        req.setAttribute("page", page);


        List<Grade> list = gradeService.getGradeByClassName(keywords, page);

        HttpSession session = req.getSession();
        session.setAttribute("GradeList", list);
        session.setAttribute("pageCount", pageCount);

        // super.processTemplate("index", req, resp);
        return "processTemplate:index";
    }

}
