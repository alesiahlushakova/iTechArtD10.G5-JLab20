package com.itechart.lab.service;

import com.itechart.lab.model.Book;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookService {
    /**
     *
     * @param offSet
     * @param numberOfRecords
     * @return
     * @throws ServiceException
     */
    Map<List<Book>, Integer> findAllBooksByPages(int offSet, int numberOfRecords)
            throws ServiceException;

    Book findBook(int id) throws ServiceException;

    Map<List<Book>, Integer> findAllBooksByPagesFiltered(int offSet, int numberOfRecords)
            throws ServiceException;

    byte[] retrieveImage(int id) throws ServiceException;

    boolean createBook(InputStream cover, String title, String publisher,
                       Date publishDate, int pageCount, String description,
                       int totalAmount, String isbn, List<Integer> authors, List<Integer> genres)
            throws ServiceException;

    boolean deleteBook(String[] books) throws ServiceException;

    List<Book> searchForBook(String title, String description, List<String> genres, List<String> authors)
            throws ServiceException;

    boolean editBook(Book book, List<Integer> authors, List<Integer> genres) throws ServiceException;

    Date calculateBookAvailability(int id) throws ServiceException;
}
