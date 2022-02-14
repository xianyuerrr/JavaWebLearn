package com.xianyue.dao;

import java.util.List;

/**
 * @auther xianyue
 * @date 2022/2/14 - 星期一 - 13:24
 **/
public class GradeDaoImpl extends BaseDao<Grade> implements GradeDao {

    /**
     * @Description 获取所有记录
     * @return
     */
    @Override
    public List<Grade> getGradeList() {
        String sql = "SELECT 课程代码 AS classCode, " +
                "课程名称 AS className, " +
                "学分 AS credit, " +
                "学年学期 AS semester, " +
                "总成绩 overallGrades, " +
                "平时成绩 AS usualGrades, " +
                "期末成绩 AS finalGrades " +
                "FROM grade;";
        return executeQuery(sql);
    }

    /**
     * @Description 根据 课程代码 获取记录，由于 课程代码 为主键，其值在表中唯一，所以最多只可能有一条记录
     * @param ClassCode 课程代码
     * @return
     */
    @Override
    public Grade getGradeByClassCode(String ClassCode) {
        String sql = "SELECT 课程代码 AS classCode, " +
                "课程名称 AS className, " +
                "学分 AS credit, " +
                "学年学期 AS semester, " +
                "总成绩 overallGrades, " +
                "平时成绩 AS usualGrades, " +
                "期末成绩 AS finalGrades " +
                "FROM grade " +
                "WHERE 课程代码 = ?;";
        List<Grade> list = executeQuery(sql, ClassCode);
        return list == null ? null :list.get(0);
    }

    /**
     * @Description 根据 课程名称 获取所有元素，为模糊查询，使用 like 匹配
     * @param ClassName 课程名称
     * @return
     */
    @Override
    public List<Grade> getGradeByClassName(String ClassName) {
        String sql = "SELECT 课程代码 AS classCode, " +
                "课程名称 AS className, " +
                "学分 AS credit, " +
                "学年学期 AS semester, " +
                "总成绩 overallGrades, " +
                "平时成绩 AS usualGrades, " +
                "期末成绩 AS finalGrades " +
                "FROM grade " +
                "WHERE 课程名称 LIKE CONCAT('%', ?, '%');";
        return executeQuery(sql, ClassName);
    }

    /**
     * @Description 插入数据，若数据已经存在，则进行更新
     * @param grade Grade 类的对象
     * @return
     */
    @Override
    public int add(Grade grade) {
        String sql = "INSERT INTO grade (课程代码, 课程名称, 学分, 学年学期, 平时成绩, 期末成绩, 总成绩) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql,
                grade.getClassCode(),
                grade.getClassName(),
                grade.getCredit(),
                grade.getSemester(),
                grade.getUsualGrades(),
                grade.getFinalGrades(),
                grade.getOverallGrades()
                );
    }

    /**
     * @Description 更新数据
     * @param grade Grade 对象
     * @return
     */
    @Override
    public int update(Grade grade) {
        String sql = "UPDATE grade SET " +
                "课程名称 = ?, " +
                "学分 = ?, " +
                "学年学期 = ?, " +
                "平时成绩 = ?," +
                "期末成绩 = ?, " +
                "总成绩 = ? " +
                "WHERE 课程代码 = ?";
        return executeUpdate(sql,
                grade.getClassName(),
                grade.getCredit(),
                grade.getSemester(),
                grade.getUsualGrades(),
                grade.getFinalGrades(),
                grade.getOverallGrades(),
                grade.getClassCode()
                );
    }

    /**
     * @Description 删除数据
     * @param grade
     * @return
     */
    @Override
    public int delete(Grade grade) {
        String sql = "DELETE FROM grade WHERE 课程代码 = ?";
        int res = executeUpdate(sql, grade.getClassCode());
        System.out.println("delete : " + res);
        return res;
    }
}
