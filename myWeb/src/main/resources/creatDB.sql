# 若数据库 me 不存在则进行创建
CREATE DATABASE IF NOT EXISTS `me`;

# 切换到数据库 me
USE `me`;

# 若数据表 grade 不存在则进行创建
CREATE TABLE IF NOT EXISTS `grade` (
    课程代码 VARCHAR(12) PRIMARY KEY,
    课程名称 VARCHAR(20) NOT NULL,
    学分 FLOAT NOT NULL,
    学年学期 VARCHAR(12),
    平时成绩 FLOAT,
    期末成绩 FLOAT,
    总成绩 FLOAT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
