package com.itechart.lab.service;

import com.itechart.lab.model.Book;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookService {
    /**
     * method finds books usong pagination
     * @param offSet current offset
     * @param numberOfRecords number of shown records
     * @return list of books, number of records
     * @throws ServiceException exception
     */
    Map<List<Book>, Integer> findAllBooksByPages(int offSet, int numberOfRecords)
            throws ServiceException;

    /**
     * method finds book by od
     * @param id id
     * @return found book
     * @throws ServiceException exception
     */
    Book findBook(int id) throws ServiceException;

    /**
     * finds only available books
     * @param offSet offset for pagination
     * @param numberOfRecords number of shown records
     * @return found books
     * @throws ServiceException exception
     */
    Map<List<Book>, Integer> findAllBooksByPagesFiltered(int offSet, int numberOfRecords)
            throws ServiceException;

    /**
     * get image
     * @param id book id
     * @return image in bytes
     * @throws ServiceException exception
     */
    byte[] retrieveImage(int id) throws ServiceException;

    /**
     * method creates book entity
     * @param cover cover
     * @param title title
     * @param publisher publisher
     * @param publishDate publishDate
     * @param pageCount page count
     * @param description description
     * @param totalAmount total amount
     * @param isbn isbn
     * @param authors authors
     * @param genres genres
     * @return is book created
     * @throws ServiceException exception
     */
    boolean createBook(InputStream cover, String title, String publisher,
                       Date publishDate, int pageCount, String description,
                       int totalAmount, String isbn, List<Integer> authors, List<Integer> genres)
            throws ServiceException;

    /**
     * method deletes books
     * @param books list of books to delete
     * @return is deleted
     * @throws ServiceException exception
     */
    boolean deleteBook(String[] books) throws ServiceException;

    /**
     * method searches for book by criteria
     * @param title title
     * @param description description
     * @param genres genres
     * @param authors authors
     * @return found books
     * @throws ServiceException exception
     */
    List<Book> searchForBook(String title, String description, List<String> genres, List<String> authors)
            throws ServiceException;

    /**
     * methos edits book
     * @param book book to edit
     * @param authors authors
     * @param genres genres
     * @return is book edited
     * @throws ServiceException exception
     */
    boolean editBook(Book book, List<Integer> authors, List<Integer> genres) throws ServiceException;

    /**
     * find book availability date
     * @param id book id
     * @return availability date
     * @throws ServiceException exception
     */
    Date calculateBookAvailability(int id) throws ServiceException;
}
