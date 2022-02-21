package com.xianyue.grade.service;

import com.xianyue.grade.dao.Grade;

import java.util.List;

/**
 * The interface Grade service.
 *
 * @Description service 层的方法属于业务方法，粒度比较粗。比较复杂，一般包含多个 dao 层方法。比如 注册
 */
public interface GradeService {
    /**
     * Gets grade list.
     *
     * @return the grade list
     */
    List<Grade> getGradeList();

    /**
     * Gets grade list.
     *
     * @param page the page
     * @return the grade list
     */
    List<Grade> getGradeList(int page);

    /**
     * Gets grade by class code.
     *
     * @param ClassCode the class code
     * @return the grade by class code
     */
    Grade getGradeByClassCode(String ClassCode);

    /**
     * Gets grade by class name.
     *
     * @param className the class name
     * @return the grade by class name
     */
    List<Grade> getGradeByClassName(String className);

    /**
     * Gets grade by class name.
     *
     * @param className the class name
     * @param page      the page
     * @return the grade by class name
     */
    List<Grade> getGradeByClassName(String className, int page);

    /**
     * Add int.
     *
     * @param grade the grade
     * @return the int
     */
    int add(Grade grade);

    /**
     * Update int.
     *
     * @param grade the grade
     * @return the int
     */
    int update(Grade grade);

    /**
     * Delete int.
     *
     * @param classCode the class code
     * @return the int
     */
    int delete(String classCode);
}
