package com.xianyue.grade.service;

import com.xianyue.grade.dao.Grade;

import java.util.List;

/**
 * @Description service 层的方法属于业务方法，粒度比较粗。比较复杂，一般包含多个 dao 层方法。比如 注册
 */
public interface GradeService {
    List<Grade> getGradeList();
    List<Grade> getGradeList(int page);
    Grade getGradeByClassCode(String ClassCode);
    List<Grade> getGradeByClassName(String className);
    List<Grade> getGradeByClassName(String className, int page);
    int add(Grade grade);
    int update(Grade grade);
    int delete(String classCode);
}
