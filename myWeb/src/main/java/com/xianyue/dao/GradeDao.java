package com.xianyue.dao;

import java.util.List;

/**
 * @auther xianyue
 * @date 2022/2/14 - 星期一 - 17:07
 **/
public interface GradeDao {
    List<Grade> getGradeList();
    List<Grade> getGradeList(int page);
    Grade getGradeByClassCode(String ClassCode);
    List<Grade> getGradeByClassName(String ClassName);
    List<Grade> getGradeByClassName(String ClassName, int page);
    int add(Grade grade);
    int update(Grade grade);
    int delete(String classCode);
}
