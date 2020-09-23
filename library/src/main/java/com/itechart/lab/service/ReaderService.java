package com.itechart.lab.service;

import com.itechart.lab.model.Reader;
import com.itechart.lab.repository.DaoException;
import com.itechart.lab.repository.ReaderDao;
import com.itechart.lab.repository.pool.ConnectionWrapper;

import java.sql.Connection;
import java.util.List;

public class ReaderService {
    private ReaderDao readerDao;

    public ReaderService() {
        readerDao = ReaderDao.getInstance();
    }

    public Reader findReader(int readerId) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return readerDao.selectEntityById(connectionWrapper.getConnection(), readerId);

        } catch (DaoException e) {
            throw new ServiceException("Error in finding a reader");
        }
    }

    public List<String> findEmails() throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return readerDao.getEmails(connectionWrapper.getConnection());
        } catch (DaoException e) {
            throw new ServiceException("Error in finding mails");
        }
    }

    public int saveReader(String email, String firstname, String lastname)
            throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            Connection connection = connectionWrapper.getConnection();
            Reader reader = new Reader();
            reader.setEmail(email);
            reader.setFirstname(firstname);
            reader.setLastname(lastname);
            readerDao.insert(connection, reader);
            return readerDao.selectIdByMail(connection, email);

        } catch (DaoException e) {
            throw new ServiceException("Error in finding a reader");
        }
    }

    public boolean editReader(int id, String email, String firstname, String lastname)
            throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            Reader reader = new Reader();
            reader.setId(id);
            reader.setEmail(email);
            reader.setFirstname(firstname);
            reader.setLastname(lastname);

            return readerDao.update(connectionWrapper.getConnection(), reader);

        } catch (DaoException e) {
            throw new ServiceException("Error in finding a reader");
        }
    }

    public int findIdByMail(String email) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return readerDao.selectIdByMail(connectionWrapper.getConnection(), email);

        } catch (DaoException e) {
            throw new ServiceException("Error in finding a reader");
        }
    }
}
