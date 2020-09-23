package com.itechart.lab.repository;

import com.itechart.lab.model.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

 abstract class AbstractDao<T extends Entity> implements Dao<T> {
     public static final int EMPTY_RESULT = 0;
     public static final String NULL_PARAMETER = "null";

     public static final String SELECT_ALL_QUERY_KEY = "SELECT_ALL";
     public static final String SELECT_BY_ID_QUERY_KEY = "SELECT_BY_ID";
     public static final String DELETE_BY_ID_QUERY_KEY = "DELETE_BY_ID";
     public static final String INSERT_ENTITY_QUERY_KEY = "INSERT_ENTITY";
     public static final String UPDATE_ENTITY_QUERY_KEY = "UPDATE_ENTITY";

     private final Map<String, String> commonQueries;

     protected AbstractDao() {
         this.commonQueries = initializeCommonQueries();
     }


     public List<T> selectAll(Connection connection) throws DaoException {
         String sqlQuery = commonQueries.get(SELECT_ALL_QUERY_KEY);

         try (Statement statement = connection.createStatement()) {

             List<T> entities = new ArrayList<T>();

             ResultSet resultSet = statement.executeQuery(sqlQuery);
             while (resultSet.next()) {
                 T entity = buildEntity(connection, resultSet);
                 entities.add(entity);
             }

             return entities;
         } catch (SQLException exception) {
             throw new DaoException(exception.getMessage(), exception);
         }
     }


     public T selectEntityById(Connection connection, final int id) throws DaoException {
         String sqlQuery = commonQueries.get(SELECT_BY_ID_QUERY_KEY);

         try (PreparedStatement preparedStatement
                      = prepareStatementForQuery(connection, sqlQuery, id)) {
             T entity = null;
             ResultSet resultSet = preparedStatement.executeQuery();
             if (resultSet.next()) {
                 entity = buildEntity(connection, resultSet);
             }

             return entity;
         } catch (SQLException exception) {
             throw new DaoException(exception.getMessage(), exception);
         }
     }


     public boolean deleteById(Connection connection, int id) throws DaoException {
         String sqlQuery = commonQueries.get(DELETE_BY_ID_QUERY_KEY);

         return executeQuery(connection, sqlQuery, id);
     }


     public boolean insert(Connection connection, final T entity) throws DaoException {
         String sqlQuery = commonQueries.get(INSERT_ENTITY_QUERY_KEY);
         List<String> parameters = getEntityParameters(entity);

         return executeQuery(connection, sqlQuery, parameters);
     }



     public boolean update(Connection connection, final T entity) throws DaoException {
         String sqlQuery = commonQueries.get(UPDATE_ENTITY_QUERY_KEY);
         List<String> parameters = getEntityParameters(entity);

         int entityId = entity.getId();
         String entityIdValue = String.valueOf(entityId);
         parameters.add(entityIdValue);

         return executeQuery(connection, sqlQuery, parameters);
     }


     protected boolean executeQuery(Connection connection, String sqlQuery, Object... parameters) throws DaoException {
         try (PreparedStatement preparedStatement = prepareStatementForQuery(connection, sqlQuery, parameters)) {
             int queryResult = preparedStatement.executeUpdate();

             return queryResult != EMPTY_RESULT;
         } catch (SQLException exception) {
             throw new DaoException(exception.getMessage(), exception);
         }
     }

     private boolean executeQuery(Connection connection, String sqlQuery, List<String> parameters) throws DaoException {
         try (PreparedStatement preparedStatement = preparedStatementForQuery(connection, sqlQuery, parameters)) {
             int queryResult = preparedStatement.executeUpdate();

             return queryResult != EMPTY_RESULT;
         } catch (SQLException exception) {
             throw new DaoException(exception.getMessage(), exception);
         }
     }


     protected PreparedStatement prepareStatementForQuery(Connection connection, String sqlQuery, Object... parameters) throws DaoException {
         try {
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

             if (parameters != null) {
                 int parameterIndex = 1;
                 for (Object parameter : parameters) {
                     if (parameter == null) {
                         preparedStatement.setNull(parameterIndex, Types.NULL);
                     } else {
                         preparedStatement.setObject(parameterIndex, parameter);
                     }
                     parameterIndex++;
                 }
             }

             return preparedStatement;
         } catch (SQLException exception) {
             throw new DaoException(exception.getMessage(), exception);
         }
     }


     private PreparedStatement preparedStatementForQuery(Connection connection, String sqlQuery, List<String> parameters) throws DaoException {
         try {
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

             if (parameters != null) {
                 int parameterIndex = 1;
                 for (String parameter : parameters) {
                     if (NULL_PARAMETER.equals(parameter)) {
                         preparedStatement.setNull(parameterIndex, Types.NULL);
                     } else {
                         preparedStatement.setString(parameterIndex, parameter);
                     }
                     parameterIndex++;
                 }
             }

             return preparedStatement;
         } catch (SQLException exception) {
             throw new DaoException(exception.getMessage(), exception);
         }

     }

     protected abstract List<String> getEntityParameters(T entity);

     protected abstract T buildEntity(Connection connection, ResultSet resultSet) throws DaoException;

     protected abstract Map<String, String> initializeCommonQueries();
}
