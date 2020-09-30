package com.itechart.lab.service;

import com.itechart.lab.model.Genre;

import java.util.List;

public interface GenreService {
    boolean saveGenre(String genre) throws ServiceException;

    List<Genre> findAllGenres() throws ServiceException;
}
