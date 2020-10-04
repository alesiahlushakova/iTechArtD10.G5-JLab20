package com.itechart.lab.service.impl;

import com.itechart.lab.model.Author;
import com.itechart.lab.model.Book;
import com.itechart.lab.repository.BookDao;
import com.itechart.lab.repository.DaoException;
import com.itechart.lab.repository.OrderDao;
import com.itechart.lab.repository.pool.ConnectionWrapper;
import com.itechart.lab.service.BookService;
import com.itechart.lab.service.ServiceException;

import java.io.InputStream;
import java.sql.Connection;
import java.util.*;

public class BookServiceImpl implements BookService {
    private BookDao bookDao;
    private OrderDao orderDao;

    private BookServiceImpl() {
        bookDao = BookDao.getInstance();
        orderDao = OrderDao.getInstance();
    }

    public static BookServiceImpl getInstance() {
        return BookServiceHolder.BOOK_SERVICE;
    }

    @Override
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

    @Override
    public Book findBook(int id) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return bookDao.selectEntityById(connectionWrapper.getConnection(), id);
        } catch (DaoException exception) {
            throw new ServiceException("Exception during finding  book", exception);
        }
    }

    @Override
    public Map<List<Book>, Integer> findAllBooksByPagesFiltered(int offSet, int numberOfRecords)
            throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            Map<List<Book>, Integer> books = new HashMap<>();

            List<Book> bookList = bookDao.selectAllAvailableBooks(connectionWrapper.getConnection(), offSet, numberOfRecords);
            Integer countOfRecords = bookDao.getNumberOfRecords();

            books.put(bookList, countOfRecords);

            return books;
        } catch (DaoException exception) {
            throw new ServiceException("Exception during finding all books by pages operation.", exception);
        }
    }

    @Override
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

    @Override
    public boolean editBook(Book book, List<Integer> authors, List<Integer> genres) throws ServiceException {
        ConnectionWrapper connectionWrapper = new ConnectionWrapper();
        try {
            connectionWrapper.startTransaction();
            Connection connection = connectionWrapper.getConnection();
            bookDao.update(connection, book);

            boolean isRelatedAuthor = bookDao.relateWithAuthors(connection, book.getId(), authors);
            boolean isRelatedGenre = bookDao.relateWithGenres(connection, book.getId(), genres);
            connectionWrapper.commitTransaction();
            connectionWrapper.endTransaction();
            return isRelatedAuthor && isRelatedGenre;


        } catch (DaoException exception) {
            connectionWrapper.rollbackTransaction();
            connectionWrapper.close();
            throw new ServiceException("Exception while updating the book genres and authors.", exception);
        }

    }


    @Override
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
            connectionWrapper.close();
            throw new ServiceException("Exception while saving the book genres and authors.", exception);
        }

    }

    @Override
    public boolean deleteBook(String[] books) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            boolean isSuccess = false;
            for (String book : books) {
                isSuccess = false;
                isSuccess = bookDao.deleteById(connectionWrapper.getConnection(), Integer.parseInt(book));
            }
            return isSuccess;
        } catch (DaoException exception) {
            throw new ServiceException("Exception while deleting the book.", exception);
        }
    }

    @Override
    public List<Book> searchForBook(String title, String description, List<String> genres, List<String> authors)
            throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return bookDao
                    .searchForBookByCriteria(connectionWrapper.getConnection(), description, title, genres, authors);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while searching for book by criteria", exception);
        }
    }


    @Override
    public Date calculateBookAvailability(int id) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return orderDao.getAvailabilityDate(connectionWrapper.getConnection(), id);
        } catch (DaoException exception) {
            throw new ServiceException("Exception during calculating book availability.", exception);
        }
    }

    private static class BookServiceHolder {
        private static final BookServiceImpl BOOK_SERVICE = new BookServiceImpl();
    }
}