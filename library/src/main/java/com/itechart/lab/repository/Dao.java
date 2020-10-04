package com.itechart.lab.repository;

import com.itechart.lab.model.Entity;

import java.sql.Connection;
import java.util.List;

public interface Dao<T extends Entity> {
    /**
     * method selects all entities
     * @param connection connection to db
     * @return entities
     * @throws DaoException exception
     */
    List<T> selectAll(Connection connection) throws DaoException;

    /**
     * method selects entity by id
     * @param connection connection to db
     * @param id entity id
     * @return entity
     * @throws DaoException exception
     */
    T selectEntityById(Connection connection, int id) throws DaoException;

    /**
     * method deletes entity by id
     * @param connection connection db
     * @param id id
     * @return is deleted
     * @throws DaoException exception
     */
    boolean deleteById(Connection connection, int id) throws DaoException;

    /**
     * method inserts entity
     * @param connection connection to db
     * @param entity entity
     * @return is inserted
     * @throws DaoException exception
     */
    boolean insert(Connection connection, T entity) throws DaoException;

    /**
     * method updates entity
     * @param connection connection to db
     * @param entity entity
     * @return is updated
     * @throws DaoException exception
     */
    boolean update(Connection connection, T entity) throws DaoException;
}
