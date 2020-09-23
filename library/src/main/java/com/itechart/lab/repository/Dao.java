package com.itechart.lab.repository;

import com.itechart.lab.model.Entity;

import java.sql.Connection;
import java.util.List;

public interface Dao<T extends Entity> {

    List<T> selectAll(Connection connection) throws DaoException;


    T selectEntityById(Connection connection, int id) throws DaoException;


    boolean deleteById(Connection connection, int id) throws DaoException;


    boolean insert(Connection connection, T entity) throws DaoException;


    boolean update(Connection connection, T entity) throws DaoException;
}
