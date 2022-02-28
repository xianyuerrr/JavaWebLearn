package com.xianyue.grade.mapper;

import com.xianyue.grade.pojo.Grade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description dao 层中方法都是单精度或者叫细粒度方法。比较简单，与数据库打交道。
 * @auther xianyue
 * @date 2022/2/14 - 星期一 - 17:07
 **/
public interface GradeMapper {
    // 表 -> 实体类 -> mapper接口 -> 映射文件

    // MyBatis 面向接口编程的两个一致：
    // 1. 映射xml文件的 namespace 要和 mapper 接口的全类名一致
    // 2. 映射xml文件的 SQL 语句的 id 要和 mapper 接口的方法名一致
    List<Grade> getGradeList();
    Grade getGradeByClassCode(@Param("classCode") String classCode);
    List<Grade> getGradeByClassName(@Param("className") String className);
    int add(Grade grade);
    int update(Grade grade);
    int delete(@Param("classCode") String classCode);
}
