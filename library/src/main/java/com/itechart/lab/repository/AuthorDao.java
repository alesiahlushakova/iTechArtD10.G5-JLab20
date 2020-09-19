package com.itechart.lab.repository;

import com.itechart.lab.model.Author;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorDao extends AbstractDao<Author> {
    /**
     * Common queries.
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM author";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM author WHERE id=?";
    private static final String SELECT_BY_INITIALS_QUERY = "SELECT * FROM author WHERE name=? ";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM author WHERE id=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO author (`name`)  VALUES(?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE author SET  name=? WHERE id=?";

    private static final String ID_COLUMN = "id";
    private static final String FIRSTNAME_COLUMN = "name";


    public AuthorDao(Connection connection) {
        super(connection);
    }

    public boolean checkAuthorForUniqueness(String fitstname) throws DaoException {
        try (PreparedStatement preparedStatement
                     = prepareStatementForQuery(SELECT_BY_INITIALS_QUERY, fitstname)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            return !resultSet.next();
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    @Override
    protected List<String> getEntityParameters(Author entity) {
        List<String> parameters = new ArrayList<>();

        String firstname = entity.getName();
        parameters.add(firstname);

        return parameters;
    }

    @Override
    protected Author buildEntity(ResultSet resultSet) throws DaoException {
        try {
            Author author = new Author();

            int id = resultSet.getInt(ID_COLUMN);
            author.setId(id);

            String firstnameColumn = resultSet.getString(FIRSTNAME_COLUMN);
            author.setName(firstnameColumn);


            return author;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    @Override
    protected Map<String, String> initializeCommonQueries() {
        Map<String, String> commonQueries = new HashMap<>();
        commonQueries.put(SELECT_ALL_QUERY_KEY, SELECT_ALL_QUERY);
        commonQueries.put(SELECT_BY_ID_QUERY_KEY, SELECT_BY_ID_QUERY);
        commonQueries.put(DELETE_BY_ID_QUERY_KEY, DELETE_BY_ID_QUERY);
        commonQueries.put(INSERT_ENTITY_QUERY_KEY, INSERT_ENTITY_QUERY);
        commonQueries.put(UPDATE_ENTITY_QUERY_KEY, UPDATE_ENTITY_QUERY);

        return commonQueries;
    }
}
