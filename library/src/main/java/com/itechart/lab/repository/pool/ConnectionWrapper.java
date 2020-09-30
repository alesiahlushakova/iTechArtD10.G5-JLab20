package com.itechart.lab.repository.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionWrapper  implements AutoCloseable{
    private static final Logger LOGGER = LogManager.getLogger(ConnectionWrapper.class);
    private final Connection connection;

    public ConnectionWrapper() {

        connection = ConnectionPool.getInstance().getConnection();
    }

    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            LOGGER.error("Transaction start failed. ");
        }
    }

    public void commitTransaction() {
        try {
            connection.commit();
        } catch (SQLException exception) {
            LOGGER.error("Transaction commit failed. ");
        }
    }

    public void rollbackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException exception) {
            LOGGER.error("Transaction rollback failed. ");
        }
    }

    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            LOGGER.error("Transaction end failed. ");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        ConnectionPool.getInstance().returnConnection(connection);
    }
}
