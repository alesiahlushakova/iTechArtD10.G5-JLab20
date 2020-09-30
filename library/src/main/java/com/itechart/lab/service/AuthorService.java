package com.itechart.lab.service;

import com.itechart.lab.model.Author;

import java.util.List;

public interface AuthorService {
    boolean saveAuthor(String author) throws ServiceException;

    List<Author> findAllAuthors() throws ServiceException;

}
