package com.xianyue.grade.dao;

import java.util.List;

/**
 * @Description dao 层中方法都是单精度或者叫细粒度方法。比较简单，与数据库打交道。
 * @auther xianyue
 * @date 2022/2/14 - 星期一 - 17:07
 **/
public interface GradeDao {
    List<Grade> getGradeList();
    List<Grade> getGradeList(int page);
    Grade getGradeByClassCode(String ClassCode);
    List<Grade> getGradeByClassName(String className);
    List<Grade> getGradeByClassName(String className, int page);
    int add(Grade grade);
    int update(Grade grade);
    int delete(String classCode);
}
