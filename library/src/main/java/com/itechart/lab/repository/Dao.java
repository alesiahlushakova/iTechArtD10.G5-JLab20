package com.itechart.lab.repository;

import com.itechart.lab.model.Entity;

import java.util.List;

public interface Dao<T extends Entity> {

    List<T> selectAll() throws DaoException;


    T selectEntityById(int id) throws DaoException;


    boolean deleteById(int id) throws DaoException;


    boolean insert(T entity) throws DaoException;


    boolean update(T entity) throws DaoException;
}
