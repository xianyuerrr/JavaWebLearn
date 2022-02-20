package com.xianyue.grade.service;

import com.xianyue.grade.dao.Grade;
import com.xianyue.grade.dao.GradeDao;
import com.xianyue.grade.dao.GradeDaoImpl;

import java.util.List;

/**
 * @Description 比 dao 稍微复杂些，面向业务逻辑，调用 dao 实现
 */
public class GradeServiceImpl implements GradeService{
    GradeDao dao = new GradeDaoImpl();

    @Override
    public List<Grade> getGradeList() {
        return dao.getGradeList();
    }

    @Override
    public List<Grade> getGradeList(int page) {
        return dao.getGradeList(page);
    }

    @Override
    public Grade getGradeByClassCode(String classCode) {
        return dao.getGradeByClassCode(classCode);
    }

    @Override
    public List<Grade> getGradeByClassName(String className) {
        return dao.getGradeByClassName(className);
    }

    @Override
    public List<Grade> getGradeByClassName(String className, int page) {
        return dao.getGradeByClassName(className, page);
    }

    @Override
    public int add(Grade grade) {
        return dao.add(grade);
    }

    @Override
    public int update(Grade grade) {
        return dao.update(grade);
    }

    @Override
    public int delete(String classCode) {
        return dao.delete(classCode);
    }
}
