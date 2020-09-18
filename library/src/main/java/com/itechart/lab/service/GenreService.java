package com.itechart.lab.service;

import com.itechart.lab.model.Book;
import com.itechart.lab.model.Genre;
import com.itechart.lab.repository.BookDao;
import com.itechart.lab.repository.DaoException;
import com.itechart.lab.repository.GenreDao;
import com.itechart.lab.repository.pool.ConnectionWrapper;

public class GenreService {

    public boolean saveGenre(String genre) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            GenreDao genreDao = new GenreDao(connectionWrapper.getConnection());
            boolean isUnique =  genreDao.checkGenreForUniqueness(genre);
            if (isUnique) {
                Genre genreEntity = new Genre();
                genreEntity.setGenre(genre);
            return genreDao.insert(genreEntity);
            }
            return false;
        } catch (DaoException exception) {
            throw new ServiceException("Exception saving the genre.", exception);
        }

    }
}
