package com.xianyue.task1;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @auther xianyue
 * @date 2022/1/30 - 星期日 - 12:11
 **/
public class MyJDBCTest {

    public static String generateConfig() {
        String path = "com/xianyue/task1/dbConfig.properties";

        return path;
    }

    @Test
    public void testGetConnection() {
        String path = "com/xianyue/task1/dbConfig.properties";
        Connection connection = getConnection(path);
    }

    public static Connection getConnection(String path) {
        InputStream inputStream;
        inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        Properties properties = new Properties();

        try {
            properties.load(inputStream);
        } catch (NullPointerException e) {
            // e.printStackTrace();
            System.out.println("读取配置文件失败！自动生成配置文件！");
            return getConnection(generateConfig());
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println("提取配置文件失败！请保证配置文件内容符合规范！");
            return null;
        }

        String url, dbName, user, password, driver;
        // url = (String) properties.get("url");
        url = properties.getProperty("url");
        dbName = properties.getProperty("dbName");
        user = properties.getProperty("user");
        password = properties.getProperty("password");
        // driver = properties.getProperty("driverClass");

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("连接 MySql 服务失败，请检查配置文件。");
            return null;
        }

        // 如果不存在对应数据库，则进行创建
        try {
            creatDbIfNotExists(connection, dbName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 关闭大连接
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 创建对接数据库的连接
        try {
            connection = DriverManager.getConnection(url+dbName, user, password);
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("连接数据库 " + dbName + " 失败，请检查配置文件。");
            return null;
        }


        return null;
    }

    private static void creatDbIfNotExists(Connection connection, String dbName) throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS " + dbName;
        PreparedStatement statement = connection.prepareStatement(sql);
        // statement.setObject(1, dbName);
        statement.execute();
    }
}
