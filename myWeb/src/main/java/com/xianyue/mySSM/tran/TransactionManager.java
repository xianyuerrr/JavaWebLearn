package com.xianyue.mySSM.tran;

import com.xianyue.mySSM.dao.ConnectionUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    public static void beginTransaction() throws SQLException {
        Connection connection = ConnectionUtils.getConnection();
        connection.setAutoCommit(false);
    }

    public static void commitTransaction() throws SQLException {
        Connection connection = ConnectionUtils.getConnection();
        connection.commit();
        ConnectionUtils.closeConnection();
    }

    public static void rollbackTransactions() throws SQLException {
        Connection connection = ConnectionUtils.getConnection();
        connection.rollback();
        ConnectionUtils.closeConnection();
    }
}
