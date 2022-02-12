package com.xianyue.statement;

import com.xianyue.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @auther xianyue
 * @date 2022/1/28 - 星期五 - 16:36
 **/
public class PrepareStatementUpdateTest {

    @Test
    public void testInsert() throws Exception {
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

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println(connection);

            // ? : 占位符
            String sql = "INSERT INTO CUSTOMERS(name, email, birth)values(?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                // 填充占位符
                statement.setString(1, "A");
                statement.setString(2, "A@gmail.com");
                statement.setDate(3, new Date(3213));

                statement.execute();
            }
        }
    }


    @Test
    public void testUpdate() throws SQLException, IOException, ClassNotFoundException {
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "UPDATE CUSTOMERS SET name = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, "A");
                statement.setInt(2, 18);

                statement.execute();
                // JDBCUtils.closeResource(connection, statement);
            }
        }
    }


    // 查询



    // 通用的增删改操作
    public void  update(String sql, Object ...args) throws SQLException, IOException, ClassNotFoundException {
        try (Connection connection = JDBCUtils.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                for (int i=0; i < args.length; i++) {
                    statement.setObject(i+1, args[i]);
                }
                statement.execute();
                // JDBCUtils.closeResource(connection, statement);
            }
        }
    }
}
