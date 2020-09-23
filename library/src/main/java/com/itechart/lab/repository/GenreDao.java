package com.itechart.lab.repository;

import com.itechart.lab.model.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreDao extends AbstractDao<Genre> {
    /**
     * Common queries.
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM genre";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM genre WHERE id=?";
    private static final String SELECT_BY_GENRE_QUERY = "SELECT * FROM genre WHERE genre=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM genre WHERE id=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO genre (`genre`)  VALUES(?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE genre SET  genre=? WHERE id=?";

    private static final String ID_COLUMN = "id";
    private static final String GENRE_COLUMN = "genre";

    private GenreDao() {
    }

    public static GenreDao getInstance() {
        return GenreDaoHolder.GENRE_DAO;
    }

    public boolean isGenreUnique(Connection connection, String genre) throws DaoException {
        try (PreparedStatement preparedStatement
                     = prepareStatementForQuery(connection, SELECT_BY_GENRE_QUERY, genre)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            return !resultSet.next();
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    @Override
    protected List<String> getEntityParameters(Genre entity) {
        List<String> parameters = new ArrayList<>();

        String genre = entity.getGenre();
        parameters.add(genre);

        return parameters;
    }

    @Override
    protected Genre buildEntity(Connection connection, ResultSet resultSet) throws DaoException {
        try {
            Genre genre = new Genre();

            int id = resultSet.getInt(ID_COLUMN);
            genre.setId(id);

            String genreColumn = resultSet.getString(GENRE_COLUMN);
            genre.setGenre(genreColumn);
            return genre;
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

    private static class GenreDaoHolder {
        private static final GenreDao GENRE_DAO = new GenreDao();
    }
}
