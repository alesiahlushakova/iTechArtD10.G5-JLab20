package com.itechart.lab.service;

import com.itechart.lab.model.Author;
import com.itechart.lab.model.Genre;
import com.itechart.lab.repository.AuthorDao;
import com.itechart.lab.repository.DaoException;
import com.itechart.lab.repository.GenreDao;
import com.itechart.lab.repository.pool.ConnectionWrapper;

import java.util.List;

public class AuthorService {
    public boolean saveAuthor(String author) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            AuthorDao authorDao = new AuthorDao(connectionWrapper.getConnection());
            boolean isUnique =  authorDao.checkAuthorForUniqueness(author);
            if (isUnique) {
                Author authorEntity = new Author();
                authorEntity.setName(author);
                return authorDao.insert(authorEntity);
            }
            return false;
        } catch (DaoException exception) {
            throw new ServiceException("Exception saving the author.", exception);
        }

    }

    public List<Author> findAllAuthors() throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            AuthorDao authorDao = new AuthorDao(connectionWrapper.getConnection());
            return authorDao.selectAll();

        } catch (DaoException exception) {
            throw new ServiceException("Exception finding authors.", exception);
        }
    }
}
