# MySQL

在进行表的列的定义时，类似于 `课程名称` 这样的中文字段可以不加 ''，而选择 `` 进行包裹。在插入数据行时遇到中文字段必须使用 '' 包裹。
注意，不能使用 ""。

## DML

### 查看数据库和数据表
```mysql
# 查看 MySQL 的数据库
SHOW DATABASES;

# 切换到数据库 me
USE me;

# 查看数据库 me 的表
SHOW TABLES;

# 查看表结构
DESCRIBE grade;
# DESC grade;
```

### 创建数据库和数据表
```mysql
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
```

### 修改数据表的列
```mysql
USE me;
# 修改 grade 表的列属性
ALTER TABLE grade MODIFY 学分 FLOAT NOT NULL;

# 修改列名和列属性
ALTER TABLE me.grade CHANGE 学分 学分1 FLOAT NOT NULL;
```


### 删除数据库和数据表
```mysql
# 删除数据表 grade
DROP TABLE IF EXISTS grade;

# 删除数据库 me
DROP DATABASE IF EXISTS me;

```


## DML

### 插入数据
```mysql
# 插入数据
USE me;
INSERT INTO grade VALUES (3050311001, '大学英语（一）', 3, '2018-2019 1', 98, 62, 80);
INSERT INTO grade (课程代码, 课程名称, 学分, 学年学期, 总成绩, 平时成绩, 期末成绩)
    VALUES (3080211001, '思想道德修养与法律基础', 2.5, '2018-2019 1', 84, 82, 86.50);
```


### 删除数据
```mysql
# 删除 grade 中的数据行
USE me;
DELETE FROM grade;
DELETE FROM grade WHERE true;
```


### 更新数据
```mysql
USE me;
UPDATE grade SET ColName = ColVal
WHERE 课程代码 = classCode
```