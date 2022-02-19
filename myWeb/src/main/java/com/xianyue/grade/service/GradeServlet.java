package com.xianyue.grade.service;

import com.xianyue.mySSM.ConfigRead;
import com.xianyue.grade.dao.Grade;
import com.xianyue.grade.dao.GradeDao;
import com.xianyue.grade.dao.GradeDaoImpl;
import com.xianyue.mySSM.service.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @auther xianyue
 * @date 2022/2/17 - 星期四 - 20:14
 **/
@WebServlet(name = "grade", value = "/grade")
public class GradeServlet extends ViewBaseServlet {
    private static GradeDao dao = new GradeDaoImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");

        String operation = req.getParameter("operation");

        if (operation == null || "".equals(operation)) {
            operation = "index";
        }

        // getDeclaredMethod：获取当前类的所有声明的方法，包括public、protected和private修饰的方法。
        // 需要注意的是，这些方法一定是在当前类中声明的，从父类中继承的不算，实现接口的方法由于有声明所以包括在内。

        // 获取当前类和父类的所有public的方法。
        // 这里的父类，指的是继承层次中的所有父类。比如说，A继承B，B继承C，那么B和C都属于A的父类。

        // 使用反射执行对应操作，避免操作种类太多时代码冗杂
        try {
            Method method = GradeServlet
                    .class
                    .getDeclaredMethod(operation, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println("不存在此类型操作 : " + operation);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println(operation + "方法执行失败");
        }
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String classCode = req.getParameter("classCode");
        if (classCode == null || classCode == "") {
            System.out.println("classCode 为空");
        }
        dao.delete(classCode);
        resp.sendRedirect("grade");
    }


    protected void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String classCode = req.getParameter("classCode");
        Grade grade = null;
        if (classCode != null && classCode != "") {
            grade = dao.getGradeByClassCode(classCode);
        }
        req.setAttribute("grade", grade);
        super.processTemplate("update", req, resp);
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
            dao.add(grade);
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }

        // 根据数据创建对象
        resp.sendRedirect("grade?operation=add");
    }



    protected void index(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        super.processTemplate("index", req, resp);
    }

}
