package com.xianyue.statement;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * @auther xianyue
 * @date 2022/1/28 - 星期五 - 15:43
 **/
public class StatementTest {

    // 使用 Statement 的弊端：需要拼写 sql 语句，且存在 sql 注入的问题
    public void testLogin() throws IOException, ClassNotFoundException, SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String user = scanner.next();
        System.out.println("请输入密码：");
        String password = scanner.next();

        String sql = "SELECT USER, PASSWORD" +
                "FROM user_table" +
                "WHERE USER = '" + user + "' AND PASSWORD = '" + password + "'";

        /*
         sql 注入，所以要使用 PrepareStatement 替换掉 Statement
         String sql = "SELECT USER, PASSWORD" +
                         "FROM user_table" +
                         "WHERE USER = '1' or ' AND PASSWORD = ' = 1 or '1' = '1'";
        */

        User returnUser = get(sql, User.class);
        if (returnUser != null) {
            System.out.println("登陆成功！");
        } else {
            System.out.println("用户名不存在或密码错误！");
        }
    }


    public <T> T get(String sql, Class<T> clazz) throws IOException, ClassNotFoundException,
            SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchFieldException {
        T t;

        Connection connection;
        Statement statement;
        ResultSet resultSet;


        InputStream inputStream = StatementTest
                .class
                .getClassLoader()
                .getResourceAsStream("com/xianyue/jdbc.properties");
        Properties properties = new Properties();
        properties.load(inputStream);


        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        Class.forName(driverClass);
        connection = DriverManager.getConnection(url, user, password);

        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        // 获取结果集的元数据
        ResultSetMetaData metaData = resultSet.getMetaData();

        // 获取结果集的列数
        int colCount = metaData.getColumnCount();

        if (resultSet.next()) {
            t = clazz.getDeclaredConstructor().newInstance();
            // t = clazz.newInstance();

            for (int i=0; i < colCount; i++) {
                String colName = metaData.getColumnLabel(i+1);
                Object columnVal = resultSet.getObject(colName);

                Field field = clazz.getDeclaredField(colName);
                field.setAccessible(true);
                field.set(t, columnVal);
            }
            return t;
        }
        return null;
    }

}
