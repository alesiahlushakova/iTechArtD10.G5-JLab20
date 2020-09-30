package com.itechart.lab.service.impl;

import com.itechart.lab.model.Author;
import com.itechart.lab.repository.AuthorDao;
import com.itechart.lab.repository.DaoException;
import com.itechart.lab.repository.pool.ConnectionWrapper;
import com.itechart.lab.service.AuthorService;
import com.itechart.lab.service.ServiceException;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private AuthorDao authorDao;

    private AuthorServiceImpl() {
        authorDao = AuthorDao.getInstance();
    }

    public static AuthorServiceImpl getInstance() {
        return AuthorServiceHolder.AUTHOR_SERVICE;
    }
    @Override
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
    @Override
    public List<Author> findAllAuthors() throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return authorDao.selectAll(connectionWrapper.getConnection());

        } catch (DaoException exception) {
            throw new ServiceException("Exception finding authors.", exception);
        }
    }

    private static class AuthorServiceHolder {
        private static final AuthorServiceImpl AUTHOR_SERVICE = new AuthorServiceImpl();
    }
}
