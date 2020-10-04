package com.itechart.lab.service;

import com.itechart.lab.model.Author;

import java.util.List;

public interface AuthorService {
    /**
     * method saves book authors to db
     * @param author book author
     * @return is operation successful
     * @throws ServiceException exception of service layer
     */
    boolean saveAuthor(String author) throws ServiceException;

    /**
     * method finds all book authors stored in db
     * @return list of found authors
     * @throws ServiceException exception of service layer
     */
    List<Author> findAllAuthors() throws ServiceException;

}
