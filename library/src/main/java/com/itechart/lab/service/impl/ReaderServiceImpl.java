package com.itechart.lab.service.impl;

import com.itechart.lab.model.Reader;
import com.itechart.lab.repository.DaoException;
import com.itechart.lab.repository.ReaderDao;
import com.itechart.lab.repository.pool.ConnectionWrapper;
import com.itechart.lab.service.ReaderService;
import com.itechart.lab.service.ServiceException;

import java.sql.Connection;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    private ReaderDao readerDao;

    private ReaderServiceImpl() {
        readerDao = ReaderDao.getInstance();
    }

    public static ReaderServiceImpl getInstance() {
        return ReaderServiceHolder.READER_SERVICE;
    }

    public Reader findReader(int readerId) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return readerDao.selectEntityById(connectionWrapper.getConnection(), readerId);

        } catch (DaoException e) {
            throw new ServiceException("Error in finding a reader");
        }
    }

    @Override
    public List<String> findEmails() throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return readerDao.getEmails(connectionWrapper.getConnection());
        } catch (DaoException e) {
            throw new ServiceException("Error in finding mails");
        }
    }

    @Override
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

    @Override
    public boolean editReader(int id,  String firstname, String lastname)
            throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            Reader reader = new Reader();
            reader.setId(id);
            reader.setFirstname(firstname);
            reader.setLastname(lastname);

            return readerDao.update(connectionWrapper.getConnection(), reader);

        } catch (DaoException e) {
            throw new ServiceException("Error in finding a reader");
        }
    }

    @Override
    public int findIdByMail(String email) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return readerDao.selectIdByMail(connectionWrapper.getConnection(), email);

        } catch (DaoException e) {
            throw new ServiceException("Error in finding a reader");
        }
    }

    private static class ReaderServiceHolder {
        private static final ReaderServiceImpl READER_SERVICE = new ReaderServiceImpl();
    }
}
