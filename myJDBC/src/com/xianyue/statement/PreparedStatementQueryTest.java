package com.xianyue.statement;

import com.xianyue.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 使用 PreparedStatement 实现针对不同表的通用查询操作
 * @auther xianyue
 * @date 2022/1/29 - 星期六 - 12:13
 **/
public class PreparedStatementQueryTest {

    @Test
    public void testGetInstance() throws Exception {
        String sql = "Select * From customers WHERE id = ?;";
        Customer customer = getInstance(Customer.class, sql, 1);
        System.out.println(customer);
    }


    @Test
    public void testGetForList() throws Exception {
        String sql = "select * from customers where id < ?;";
        List<Customer> list = getForList(Customer.class, sql, 12);
        list.forEach(System.out::println);
    }

    public <T> List<T> getForList(Class<T> clazz, String sql, Object ...args) throws Exception {
        Connection coon = JDBCUtils.getConnection();
        PreparedStatement statement = coon.prepareStatement(sql);
        for (int i=0; i < args.length; i++) {
            statement.setObject(i+1, args[i]);
        }

        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // 创建集合
        ArrayList<T> list = new ArrayList<>();
        while (resultSet.next()) {
            T t = clazz.getDeclaredConstructor().newInstance();

            for (int i=0; i < columnCount; i++) {
                Object value = resultSet.getObject(i+1);
                String columnLabel = metaData.getColumnLabel(i+1);

                Field field = clazz.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(t, value);
            }
            list.add(t);
        }

        return list;
    }



    /**
     * @Description 针对不同表的通用查询，返回一条数据
     * @param clazz 返回值类的 class
     * @param sql sql 语句
     * @param args 参数列表（填充 sql 语句占位符）
     * @param <T> 泛型
     * @return 泛型 <T>
     */
    public <T> T getInstance(Class<T> clazz,
                             String sql,
                             Object ...args) throws Exception {
        Connection coon = JDBCUtils.getConnection();
        PreparedStatement statement = coon.prepareStatement(sql);
        for (int i=0; i < args.length; i++) {
            statement.setObject(i+1, args[i]);
        }

        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        if (resultSet.next()) {
            T t = clazz.getDeclaredConstructor().newInstance();

            for (int i=0; i < columnCount; i++) {
                Object value = resultSet.getObject(i+1);
                String columnLabel = metaData.getColumnLabel(i+1);

                Field field = clazz.getDeclaredField(columnLabel);
                field.setAccessible(true);
                field.set(t, value);
            }
            return t;
        }

        return null;
    }
}
