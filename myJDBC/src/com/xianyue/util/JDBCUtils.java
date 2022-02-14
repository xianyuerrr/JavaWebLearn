package com.xianyue.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @auther xianyue
 * @date 2022/1/28 - 星期五 - 16:52
 **/
public class JDBCUtils {

    /**
     * @Description 获取数据库连接
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, IOException, SQLException {
        InputStream inputStream = ClassLoader
                .getSystemClassLoader()
                .getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(inputStream);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        Class.forName(driverClass);

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            return connection;
        }
    }

    /**
     * @Description 关闭资源
     * @param connection
     * @param statement
     */
    public static void closeResource(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
