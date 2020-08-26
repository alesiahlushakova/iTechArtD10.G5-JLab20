package com.itechart.lab.service;

import com.itechart.lab.model.Book;
import com.itechart.lab.repository.BookDao;
import com.itechart.lab.repository.DaoException;
import com.itechart.lab.repository.OrderDao;
import com.itechart.lab.repository.pool.ConnectionWrapper;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService {


    public Map<List<Book>, Integer> findAllBooksByPages(int offSet, int numberOfRecords) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            BookDao bookDao = new BookDao(connectionWrapper.getConnection());

            Map<List<Book>, Integer> books = new HashMap<>();

            List<Book> bookList = bookDao.selectAllBooks(offSet, numberOfRecords);
            Integer countOfRecords = bookDao.getNumberOfRecords();

            books.put(bookList, countOfRecords);

            return books;
        } catch (DaoException exception) {
            throw new ServiceException("Exception during finding all books by pages operation.", exception);
        }
    }

    public Map<List<Book>, Integer> findAllBooksByPagesFiltered(int offSet, int numberOfRecords) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            BookDao bookDao = new BookDao(connectionWrapper.getConnection());

            Map<List<Book>, Integer> books = new HashMap<>();

            List<Book> bookList = bookDao.selectAllBooks(offSet, numberOfRecords);
            Integer countOfRecords = bookDao.getNumberOfRecords();

            books.put(bookList, countOfRecords);

            return books;
        } catch (DaoException exception) {
            throw new ServiceException("Exception during finding all books by pages operation.", exception);
        }
    }

    public byte[] retrieveImage(int id) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            BookDao bookDao = new BookDao(connectionWrapper.getConnection());
            return bookDao.selectImageById(id);
        } catch (DaoException exception) {
            throw new ServiceException("Exception during image retrieval operation.", exception);
        }
    }

    public boolean isValidStatus(boolean status, int id) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            BookDao bookDao = new BookDao(connectionWrapper.getConnection());
            int amount = bookDao.getAmountOfAvailableBooks(status, id);
            if (amount > 0) {
                return !status;
            } else {
                bookDao.updateStatus(false, id);
                return false;
            }
        } catch (DaoException exception) {
            throw new ServiceException("Exception during book status validation.", exception);
        }
    }

    public boolean saveBook(Book book) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            BookDao bookDao = new BookDao(connectionWrapper.getConnection());
            return bookDao.insert(book);
        } catch (DaoException exception) {
            throw new ServiceException("Exception saving the book.", exception);
        }

    }

    public boolean createBook(InputStream cover, String title, String publisher,
                              Date publishDate, int pageCount, String description,
                              int totalAmount, String isbn, boolean status) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            BookDao bookDao = new BookDao(connectionWrapper.getConnection());
            Book book = new Book();
            book.setInputStream(cover);
            book.setTitle(title);
            book.setPublisher(publisher);
            book.setPublishDate((java.sql.Date) publishDate);
            book.setPageCount(pageCount);
            book.setDescription(description);
            book.setTotalAmount(totalAmount);
            book.setRemainingAmount(totalAmount);
            book.setISBN(isbn);
            book.setStatus(status);
            return bookDao.insert(book);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while saving the book.", exception);
        }

    }

    public boolean deleteBook(int id) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            BookDao bookDao = new BookDao(connectionWrapper.getConnection());
            return bookDao.deleteById(id);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while deleting the book.", exception);
        }
    }

    public boolean editBook(InputStream cover, String title, String publisher,
                            Date publishDate, int pageCount, String description,
                            int totalAmount, String isbn, boolean status) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            BookDao bookDao = new BookDao(connectionWrapper.getConnection());
            Book book = new Book();
            book.setInputStream(cover);
            book.setTitle(title);
            book.setPublisher(publisher);
            book.setPublishDate((java.sql.Date) publishDate);
            book.setPageCount(pageCount);
            book.setDescription(description);
            book.setTotalAmount(totalAmount);
            book.setISBN(isbn);
            book.setStatus(status);
            return bookDao.update(book);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while updating the book.", exception);
        }

    }

    public Date calculateBookAvailability(int id) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            OrderDao bookDao = new OrderDao(connectionWrapper.getConnection());
            return bookDao.getAvailabilityDate(id);
        } catch (DaoException exception) {
            throw new ServiceException("Exception during calculating book availability.", exception);
        }
    }

}