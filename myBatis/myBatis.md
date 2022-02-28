# MyBatis

## 介绍
原名 iBatis，来源于 internet 和 abatis 的组合，是一个基于 Java 的持久层框架。包括 SQL Maps 和 Data Access Object(DAO)。

特性：
- 支持定制化 SQL、存储过程以及高级映射的优秀持久层框架
- 几乎避免了所有 JDBC 代码和手动设置参数以及获取结果集
- 可以使用简单的 XML 或注解用于配置和原始映射，将接口和 Java 的 POJO(Plain Old Java Objects, 普通 Java 对象) 映射成数据库中的记录
- 是一个半自动的 ORM(Object Relation Mapping) 框架


## 配置

核心配置文件中的标签必须按照固定的顺序：
properties?,settings?,typeAliases?,typeHandlers?,objectFactory?,objectWrapperFactory?,reflectorFactory?,plugins? ,
environments?,databaseIdProvider?,mappers?

## log4j

FATAL(致命)>ERROR(错误)>WARN(警告)>INFO(信息)>DEBUG(调试)
从左到右打印的内容越来越详细

## 操作

### 增
```xml
<!--int insertUser();-->
<insert id="insertUser">
    insert into t_user values(null,'admin','123456',23,'男')
</insert>
```

### 删
```xml
<!--int deleteUser();-->
<delete id="deleteUser">
    delete from t_user where id = 7
</delete>
```

### 改
```xml
<!--int updateUser();-->
<update id="updateUser">
    update t_user set username='ybc',password='123' where id = 6
</update>
```

### 查
查询时必须设置 resultType 或 resultMap
- resultType : 设置默认的映射关系
- resultMap : 设置自定义的映射关系

#### 查一个
```xml
<!--User getUserById();-->
<select id="getUserById" resultType="com.atguigu.mybatis.bean.User">
    select * from t_user where id = 2
</select>
```

#### 查一组
```xml
<!--List<User> getUserList();-->
<select id="getUserList" resultType="com.atguigu.mybatis.bean.User">
    select * from t_user
</select>
```


### mapper 配置文件
```xml
<!-- MyBatis 获取参数值的两种方式：${} 和 #{} -->
    <!-- ${} 本质是字符串拼接，当参数为字符串时，需要加 '' -->
    <!-- #{} 本质是占位符 -->

    <!-- 参数为单个时，随便写 #{aaa} 之类的都行。 -->

    <!-- 参数为多个时，需要使用诸如 {arg0}, {arg1} 或 {param1}, {param2} 之类为值 -->
    <!-- 参数为多个时，也可以在定义参数方法时，主动将参数列表的参数放入 map 集合中，就可以通过 {username} 这样的方式进行访问 -->

    <!-- 参数为实体类类型的参数时，可以以 {classCode} 访问其 classCode 属性 -->

    <!-- 也可以使用 @Param 注解，就可以使用注解的值作为键，此时 {arg0} 这样的就无法使用了，@Param 注解的默认值就是 {arg0} 这样的 -->



    <!-- 解决数据库列名与类属性名不一致的情况 3 : 在 mapper 文件中自定义 resultMap 映射 -->
    <!-- 不过 resultMap 更多时候是用来处理 多表连接时的 多对一的映射关系 比如 studentId 和 Student -->
    <!-- <resultMap id="" type=""> -->
    <!--     &lt;!&ndash; id 标签自定义主键映射 &ndash;&gt; -->
    <!--     <id property="" column="" /> -->

    <!--     &lt;!&ndash; result 标签自定义非主键列映射 &ndash;&gt; -->
    <!--     <result property="" column="" /> -->


    <!--     &lt;!&ndash; 级联对象属性多对一赋值 &ndash;&gt; -->
    <!--     <result property="student.name" column="name" /> -->

    <!--     &lt;!&ndash; 专门处理多对一属性赋值的标签 &ndash;&gt; -->
    <!--     &lt;!&ndash; 方式一 &ndash;&gt; -->
    <!--     <association property="student" javaType=""> -->
    <!--         <id property="" column="" /> -->
    <!--         <result property="" column="" /> -->
    <!--     </association> -->
    <!--     &lt;!&ndash; 方式二，在懒加载开启时，只有在访问与 student 相关内容时才会执行。可以通过 fetchType 的值来控制是否懒加载 &ndash;&gt; -->
    <!--     &lt;!&ndash; fetchType = "lazy | eager" &ndash;&gt; -->
    <!--     <association property="property" select="com.xianyue.grade.mapper.StudentMapper.getStudentById" -->
    <!--                  column="studentId" /> -->
    <!-- </resultMap> -->

    <!-- <select id="getGradeList" resultType="com.xianyue.grade.pojo.Grade"> -->
    <!--     &lt;!&ndash; 解决数据库列名与类属性名不一致的情况 1 : sql中设置别名 &ndash;&gt; -->
    <!--     #         select * from grade -->
    <!--     select a As aa from grade -->
    <!-- </select> -->
```