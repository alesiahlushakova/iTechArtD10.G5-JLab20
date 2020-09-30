package com.itechart.lab.service.impl;

import com.itechart.lab.model.Genre;
import com.itechart.lab.repository.DaoException;
import com.itechart.lab.repository.GenreDao;
import com.itechart.lab.repository.pool.ConnectionWrapper;
import com.itechart.lab.service.GenreService;
import com.itechart.lab.service.ServiceException;

import java.sql.Connection;
import java.util.List;
public class GenreServiceImpl implements GenreService {
    private GenreDao genreDao;

    private GenreServiceImpl() {
        genreDao = GenreDao.getInstance();
    }

    public static GenreServiceImpl getInstance() {
        return GenreServiceHolder.GENRE_SERVICE;
    }

    @Override
    public boolean saveGenre(String genre) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            Connection connection = connectionWrapper.getConnection();
            boolean isUnique = genreDao.isGenreUnique(connection, genre);
            if (isUnique) {
                Genre genreEntity = new Genre();
                genreEntity.setGenre(genre);
                return genreDao.insert(connection, genreEntity);
            }
            return false;
        } catch (DaoException exception) {
            throw new ServiceException("Exception saving the genre.", exception);
        }

    }

    @Override
    public List<Genre> findAllGenres() throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return genreDao.selectAll(connectionWrapper.getConnection());
        } catch (DaoException exception) {
            throw new ServiceException("Exception finding genres.", exception);
        }
    }

    private static class GenreServiceHolder {
        private static final GenreServiceImpl GENRE_SERVICE = new GenreServiceImpl();
    }
}
