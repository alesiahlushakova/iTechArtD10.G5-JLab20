package com.itechart.lab.service;

import java.util.List;

public interface ReaderService {
    List<String> findEmails() throws ServiceException;

    int saveReader(String email, String firstname, String lastname)
            throws ServiceException;

    boolean editReader(int id, String firstname, String lastname)
            throws ServiceException;

    int findIdByMail(String email) throws ServiceException;
}
