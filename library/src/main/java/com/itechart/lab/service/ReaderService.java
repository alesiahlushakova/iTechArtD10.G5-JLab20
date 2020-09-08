package com.itechart.lab.service;

import com.itechart.lab.model.Reader;
import com.itechart.lab.repository.DaoException;
import com.itechart.lab.repository.ReaderDao;
import com.itechart.lab.repository.pool.ConnectionWrapper;

public class ReaderService {

    public Reader findReader(int readerId) throws ServiceException{
        try(ConnectionWrapper connectionWrapper = new ConnectionWrapper()){
            ReaderDao readerDao = new ReaderDao(connectionWrapper.getConnection());
            return readerDao.selectEntityById(readerId);

        } catch (DaoException e) {
            throw new ServiceException("Error in finding a reader");
        }
    }

    public boolean saveReader(String email, String firstname, String lastname)
        throws ServiceException{
        try(ConnectionWrapper connectionWrapper = new ConnectionWrapper()){
            ReaderDao readerDao = new ReaderDao(connectionWrapper.getConnection());
          Reader reader = new Reader();
          reader.setEmail(email);
          reader.setFirstname(firstname);
          reader.setLastname(lastname);

            return readerDao.insert(reader);

        } catch (DaoException e) {
            throw new ServiceException("Error in finding a reader");
        }
    }

    public boolean editReader(int id,String email, String firstname, String lastname)
            throws ServiceException{
        try(ConnectionWrapper connectionWrapper = new ConnectionWrapper()){
            ReaderDao readerDao = new ReaderDao(connectionWrapper.getConnection());
            Reader reader = new Reader();
            reader.setId(id);
            reader.setEmail(email);
            reader.setFirstname(firstname);
            reader.setLastname(lastname);

            return readerDao.update(reader);

        } catch (DaoException e) {
            throw new ServiceException("Error in finding a reader");
        }
    }

    public int findIdByMail(String email) throws ServiceException{
        try(ConnectionWrapper connectionWrapper = new ConnectionWrapper()){
            ReaderDao readerDao = new ReaderDao(connectionWrapper.getConnection());
            return readerDao.selectIdByMail(email);

        } catch (DaoException e) {
            throw new ServiceException("Error in finding a reader");
        }
    }
}
