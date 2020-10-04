package com.itechart.lab.service;

import com.itechart.lab.model.Genre;

import java.util.List;

public interface GenreService {
    /**
     * method saves genre
     * @param genre genre
     * @return is saved
     * @throws ServiceException exception
     */
    boolean saveGenre(String genre) throws ServiceException;

    /**
     * method finds all genres
     * @return genres
     * @throws ServiceException exception
     */
    List<Genre> findAllGenres() throws ServiceException;
}
