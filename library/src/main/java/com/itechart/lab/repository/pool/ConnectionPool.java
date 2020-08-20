package com.itechart.lab.repository.pool;

import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class ConnectionPool {
    private final static Logger LOGGER = (Logger) LogManager.getLogger(ConnectionPool.class);

    private static Lock instanceLocker = new ReentrantLock();
    private static Lock poolLocker = new ReentrantLock();
    private static Condition poolCondition = poolLocker.newCondition();

    private static ConnectionPool instance = null;
    private static AtomicBoolean isInstanceAvailable = new AtomicBoolean(true);

    private final LinkedList<Connection> pool;

    private ConnectionPool() {
        ConnectionMaker connectionCreator = new ConnectionMaker();
        pool = connectionCreator.createPool();
    }


    public static ConnectionPool getInstance() {

        if (isInstanceAvailable.get()) {
            instanceLocker.lock();
            try {
                boolean isInstanceAvailableNow = instance == null;
                if (isInstanceAvailableNow) {
                    instance = new ConnectionPool();
                    isInstanceAvailable.set(false);
                }
            } finally {
                instanceLocker.unlock();
            }
        }

        return instance;
    }


    public Connection getConnection() {
        Connection connection;
        poolLocker.lock();

        try {

            if (pool.isEmpty()) {
                poolCondition.await();
            }

            connection = pool.poll();
        } catch (InterruptedException exception) {
            throw new IllegalStateException("Can't get connection. ", exception);
        } finally {
            poolLocker.unlock();
        }

        return connection;
    }


    public void returnConnection(Connection connection) {
        poolLocker.lock();

        try {
            pool.addLast(connection);
            poolCondition.signal();
        } finally {
            poolLocker.unlock();
        }
    }
}
