package com.xianyue.mySSM.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.xianyue.mySSM.ConfigRead.MySqlConfig;

public class ConnectionUtils {
    public static String driver, url, user, pwd;
    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        loadConfig();
    }

    private static void loadConfig() {
        driver = MySqlConfig.get("driverClass");
        url = MySqlConfig.get("url");
        user = MySqlConfig.get("user");
        pwd = MySqlConfig.get("pwd");

        // 加载驱动
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
        }
    }

    private static Connection creatConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection() {
        Connection connection = threadLocal.get();
        if (connection != null) {
            return connection;
        }
        connection = creatConnection();
        threadLocal.set(connection);
        return threadLocal.get();
    }

    public static void closeConnection() {
        Connection connection = threadLocal.get();
        try {
            if (connection.isClosed()) {
                connection.close();
                threadLocal.set(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
