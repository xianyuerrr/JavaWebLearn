package com.xianyue.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther xianyue
 * @date 2022/2/14 - 星期一 - 13:47
 **/
public class Grade {
    private String classCode;
    private String className;
    private Float credit;
    private String semester;
    private Float usualGrades;
    private Float finalGrades;
    private Float overallGrades;

    private static final Map<String, String> map = new HashMap<>();
    static {
        map.put("classCode", "课程代码");
        map.put("className", "课程名称");
        map.put("credit", "学分");
        map.put("semester", "学年学期");
        map.put("usualGrades", "平时成绩");
        map.put("finalGrades", "期末成绩");
        map.put("overallGrades", "总成绩");
    }

    public Grade() {}

    public Grade(String classCode, String className, Float credit, String semester, Float usualGrades, Float finalGrades, Float overallGrades) {
        this.classCode = classCode;
        this.className = className;
        this.credit = credit;
        this.semester = semester;
        this.usualGrades = usualGrades;
        this.finalGrades = finalGrades;
        this.overallGrades = overallGrades;
    }

    public String getClassCode() {
        return classCode;
    }

    public String getClassName() {
        return className;
    }

    public Float getCredit() {
        return credit;
    }

    public String getSemester() {
        return semester;
    }

    public Float getUsualGrades() {
        return usualGrades;
    }

    public Float getFinalGrades() {
        return finalGrades;
    }

    public Float getOverallGrades() {
        return overallGrades;
    }

    public void setCredit(Float credit) {
        if (credit <= 0) {
            System.out.println("credit 应大于0");
            return;
        }
        this.credit = credit;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setUsualGrades(Float usualGrades) {
        if (!checkGrades(usualGrades)) {
            return;
        }
        this.usualGrades = usualGrades;
        updateOverallGrades();
    }

    public void setFinalGrades(Float finalGrades) {
        if (!checkGrades(finalGrades)) {
            return;
        }
        this.finalGrades = finalGrades;
        updateOverallGrades();
    }

    private void updateOverallGrades() {
        this.overallGrades = (this.usualGrades + finalGrades) / 2;
    }

    private boolean checkGrades(Float grades) {
        boolean res = usualGrades >= 0 && usualGrades <= 100;
        if (!res) {
            System.out.println("成绩应在 0 到 100 之间");
        }
        return res;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "classCode='" + classCode + '\'' +
                ", className='" + className + '\'' +
                ", credit=" + credit +
                ", semester='" + semester + '\'' +
                ", usualGrades=" + usualGrades +
                ", finalGrades=" + finalGrades +
                ", overallGrades=" + overallGrades +
                '}';
    }
}
