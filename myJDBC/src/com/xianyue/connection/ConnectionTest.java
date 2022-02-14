package com.xianyue.connection;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @auther xianyue
 * @date 2022/1/27 - 星期四 - 21:24
 **/
public class ConnectionTest {
    // url 中不能加空格，会报错
    static String url = "jdbc:mysql://localhost:3306";
    static String user = "root";
    static String password = "123456";
    static String driverClass = "com.mysql.cj.jdbc.Driver";

    // 将用户名和密码封装在 Properties 中
    static Properties info = new Properties();
    static {
        info.setProperty("user", user);
        info.setProperty("password", password);
    }

    @Test
    public void testConnection1() throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();

        try (Connection connection = driver.connect(url, info)) {
            System.out.println(connection);
        }
    }


    @Test
    public void testConnection2() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException, SQLException {
        // 使用反射获取 Driver 实现类对象
        Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        // 已经弃用的写法
        // Driver driver = (Driver) clazz.newInstance();
        Driver driver = (Driver) clazz.getDeclaredConstructor().newInstance();

        try (Connection connection = driver.connect(url, info)) {
            System.out.println(connection);
        }
    }


    // 使用 DriverManager
    @Test
    public void testConnection3() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, SQLException {
        Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        // public class Driver extends NonRegisteringDriver implements java.sql.Driver {
        //     public Driver() throws SQLException {
        //     }
        //
        //     static {
        //         try {
        //             DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        //         } catch (SQLException var1) {
        //             throw new RuntimeException("Can't register driver!");
        //         }
        //     }
        // }
        //

        // 已经弃用的写法
        // Driver driver = (Driver) clazz.newInstance();
        Driver driver = (Driver) clazz.getDeclaredConstructor().newInstance();

        // 不显式注册也可，Driver 在创建时有 static 代码块自动注册
        // // 注册驱动
        // DriverManager.registerDriver(driver);

        // 获取连接
        try (Connection connection = DriverManager.getConnection(url, info)) {
            System.out.println(connection);
        }
        // DriverManager.getConnection(url, user, password);
    }


    // 使用配置文件，将信息写在配置文件里
    // 实现了数据和代码的分离，解耦
    @Test
    public void  testConnection5() throws IOException, ClassNotFoundException, SQLException {
        // 读取配置文件
        InputStream inputStream = ConnectionTest
                .class
                .getClassLoader()
                .getResourceAsStream("jdbc.properties");

        Properties pros = new Properties();
        pros.load(inputStream);

        user = pros.getProperty("user");
        password = pros.getProperty("password");
        url = pros.getProperty("url");
        driverClass = pros.getProperty("driverClass");

        // 加载驱动
        Class.forName(driverClass);
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println(connection);
        }

    }
}
