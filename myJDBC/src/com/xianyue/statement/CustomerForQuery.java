package com.xianyue.statement;

import com.xianyue.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @auther xianyue
 * @date 2022/1/29 - 星期六 - 10:34
 **/
public class CustomerForQuery {

    /*
    ORM 编程思想（object relational mapping）
    1. 一个数据表对应一个 Java 类
    2. 表中的一条记录对应 Java 类的一个对象
    3. 表中的一个字段对应 Java 类的一个属性
     */


    // 针对 Customer 表的通用查询
    public Customer queryForCustomers(String sql, Object ...args) throws Exception {
        Connection conn = JDBCUtils.getConnection();

        PreparedStatement statement = conn.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            statement.setObject(i+1, args[i]);
        }

        ResultSet rs = statement.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();

        // 假设只有一条数据，多条数据使用 while，返回 Customer 集合
        if (rs.next()) {
            Customer customer = new Customer();
            // 处理一行数据中的每一列
            for (int i=0; i < colCount; i++) {
                Object value = rs.getObject(i+1);

                // 获取列名，但是当列名与 类 的属性名对应不上时，就需要定义并使用 类的属性名 作为结果集的别名
                // 未起别名时，别名就是列名
                // String columnName = rsmd.getColumnName(i + 1);
                String columnLabel = rsmd.getColumnLabel(i + 1);

                // 给 customer 对象指定的某个属性，赋值为 value，通过反射
                Field field = Customer.class.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(customer, value);
            }
            return customer;
        }
        JDBCUtils.closeResource(conn, statement);
        return null;
    }
}
