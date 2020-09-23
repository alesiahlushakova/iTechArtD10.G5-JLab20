package com.itechart.lab.service;

import com.itechart.lab.model.Book;
import com.itechart.lab.repository.BookDao;
import com.itechart.lab.repository.DaoException;
import com.itechart.lab.repository.OrderDao;
import com.itechart.lab.repository.pool.ConnectionWrapper;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService {
    private BookDao bookDao;
    private OrderDao orderDao;

    public BookService() {
        bookDao = BookDao.getInstance();
        orderDao = OrderDao.getInstance();
    }

    public Map<List<Book>, Integer> findAllBooksByPages(int offSet, int numberOfRecords) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            Map<List<Book>, Integer> books = new HashMap<>();

            List<Book> bookList = bookDao.selectAllBooks(connectionWrapper.getConnection(), offSet, numberOfRecords);
            Integer countOfRecords = bookDao.getNumberOfRecords();

            books.put(bookList, countOfRecords);

            return books;
        } catch (DaoException exception) {
            throw new ServiceException("Exception during finding all books by pages operation.", exception);
        }
    }

    public Book findBook(int id) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return bookDao.selectEntityById(connectionWrapper.getConnection(), id);
        } catch (DaoException exception) {
            throw new ServiceException("Exception during finding  book", exception);
        }
    }

    public Map<List<Book>, Integer> findAllBooksByPagesFiltered(int offSet, int numberOfRecords)
            throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            Map<List<Book>, Integer> books = new HashMap<>();

            List<Book> bookList = bookDao.selectAllBooks(connectionWrapper.getConnection(), offSet, numberOfRecords);
            Integer countOfRecords = bookDao.getNumberOfRecords();

            books.put(bookList, countOfRecords);

            return books;
        } catch (DaoException exception) {
            throw new ServiceException("Exception during finding all books by pages operation.", exception);
        }
    }

    public byte[] retrieveImage(int id) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return bookDao.selectImageById(connectionWrapper.getConnection(), id);
        } catch (DaoException exception) {
            throw new ServiceException("Exception during image retrieval operation.", exception);
        }
    }

    public boolean isValidStatus(boolean status, int id) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            Connection connection = connectionWrapper.getConnection();
            int amount = bookDao.getAmountOfAvailableBooks(connection, status, id);
            if (amount > 0) {
                return !status;
            } else {
                bookDao.updateStatus(connection, false, id);
                return false;
            }
        } catch (DaoException exception) {
            throw new ServiceException("Exception during book status validation.", exception);
        }
    }

    public boolean saveBook(Book book) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return bookDao.insert(connectionWrapper.getConnection(), book);
        } catch (DaoException exception) {
            throw new ServiceException("Exception saving the book.", exception);
        }

    }

    public boolean createBook(InputStream cover, String title, String publisher,
                              Date publishDate, int pageCount, String description,
                              int totalAmount, String isbn, List<Integer> authors, List<Integer> genres)
            throws ServiceException {
        ConnectionWrapper connectionWrapper = new ConnectionWrapper();
        try {
            connectionWrapper.startTransaction();
            Connection connection = connectionWrapper.getConnection();
            int id = bookDao.insertBook(connection, cover, title, publisher,
                                        publishDate, pageCount, description, totalAmount, isbn, 1);
            if (id > 0) {
                boolean isRelatedAuthor = bookDao.relateWithAuthors(connection, id, authors);
                boolean isRelatedGenre = bookDao.relateWithGenres(connection, id, genres);
                connectionWrapper.commitTransaction();
                connectionWrapper.endTransaction();
                return isRelatedAuthor && isRelatedGenre;
            }
            return false;
        } catch (DaoException exception) {
            connectionWrapper.rollbackTransaction();
            throw new ServiceException("Exception while saving the book genres and authors.", exception);
        }

    }

    public boolean deleteBook(int id) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return bookDao.deleteById(connectionWrapper.getConnection(), id);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while deleting the book.", exception);
        }
    }

    public List<Book> searchForBook(String title, String description, List<String> genres, List<String> authors)
            throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return bookDao
                    .searchForBookByCriteria(connectionWrapper.getConnection(), description, title, genres, authors);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while searching for book by criteria", exception);
        }
    }

    public boolean editBook(Book book) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return bookDao.update(connectionWrapper.getConnection(), book);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while updating the book.", exception);
        }

    }

    public Date calculateBookAvailability(int id) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return orderDao.getAvailabilityDate(connectionWrapper.getConnection(), id);
        } catch (DaoException exception) {
            throw new ServiceException("Exception during calculating book availability.", exception);
        }
    }

}