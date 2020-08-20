package com.itechart.lab.repository.pool;

import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectionWrapper  implements AutoCloseable{
    private static final Logger LOGGER = (Logger) LogManager.getLogger(ConnectionWrapper.class);
    private final Connection connection;


    public ConnectionWrapper() {

        connection = ConnectionPool.getInstance().getConnection();
    }


    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            LOGGER.severe("Transaction start failed. ");
        }
    }


    public void commitTransaction() {
        try {
            connection.commit();
        } catch (SQLException exception) {
            LOGGER.severe("Transaction commit failed. ");
        }
    }


    public void rollbackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException exception) {
            LOGGER.severe("Transaction rollback failed. ");
        }
    }


    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            LOGGER.severe("Transaction end failed. ");
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
