package com.xianyue.grade.service;

import com.xianyue.grade.dao.Grade;
import com.xianyue.grade.dao.GradeDao;

import java.util.List;

/**
 * @Description 比 dao 稍微复杂些，面向业务逻辑，调用 dao 实现
 */
public class GradeServiceImpl implements GradeService{
    private static GradeDao gradeDao = null;

    @Override
    public List<Grade> getGradeList() {
        return gradeDao.getGradeList();
    }

    @Override
    public List<Grade> getGradeList(int page) {
        return gradeDao.getGradeList(page);
    }

    @Override
    public Grade getGradeByClassCode(String classCode) {
        return gradeDao.getGradeByClassCode(classCode);
    }

    @Override
    public List<Grade> getGradeByClassName(String className) {
        return gradeDao.getGradeByClassName(className);
    }

    @Override
    public List<Grade> getGradeByClassName(String className, int page) {
        return gradeDao.getGradeByClassName(className, page);
    }

    @Override
    public int add(Grade grade) {
        return gradeDao.add(grade);
    }

    @Override
    public int update(Grade grade) {
        return gradeDao.update(grade);
    }

    @Override
    public int delete(String classCode) {
        return gradeDao.delete(classCode);
    }
}
