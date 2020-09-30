package com.itechart.lab.service;

import com.itechart.lab.model.Author;
import com.itechart.lab.repository.AuthorDao;
import com.itechart.lab.repository.DaoException;
import com.itechart.lab.repository.pool.ConnectionWrapper;

import java.util.List;

public class AuthorService {
    private AuthorDao authorDao;

    private AuthorService() {
        authorDao = AuthorDao.getInstance();
    }

    public static AuthorService getInstance() {
        return AuthorServiceHolder.AUTHOR_SERVICE;
    }

    public boolean saveAuthor(String author) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            boolean isUnique = authorDao.isAuthorUnique(connectionWrapper.getConnection(), author);
            if (isUnique) {
                Author authorEntity = new Author();
                authorEntity.setName(author);
                return authorDao.insert(connectionWrapper.getConnection(), authorEntity);
            }
            return false;
        } catch (DaoException exception) {
            throw new ServiceException("Exception saving the author.", exception);
        }

    }

    public List<Author> findAllAuthors() throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return authorDao.selectAll(connectionWrapper.getConnection());

        } catch (DaoException exception) {
            throw new ServiceException("Exception finding authors.", exception);
        }
    }

    private static class AuthorServiceHolder {
        private static final AuthorService AUTHOR_SERVICE = new AuthorService();
    }
}
