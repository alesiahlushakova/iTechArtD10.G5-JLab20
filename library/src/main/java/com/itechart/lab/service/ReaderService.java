package com.itechart.lab.service;

import java.util.List;

public interface ReaderService {
    /**
     * method finds all emails
     * @return emails
     * @throws ServiceException exception
     */
    List<String> findEmails() throws ServiceException;

    /**
     * method saves reader
     * @param email email
     * @param firstname first name
     * @param lastname last name
     * @return is saved
     * @throws ServiceException exception
     */
    int saveReader(String email, String firstname, String lastname)
            throws ServiceException;

    /**
     * method edits reader
     * @param id reader id
     * @param firstname first name
     * @param lastname last name
     * @return is edited
     * @throws ServiceException exception
     */
    boolean editReader(int id, String firstname, String lastname)
            throws ServiceException;

    /**
     * method finds id by mail
     * @param email email
     * @return id
     * @throws ServiceException exception
     */
    int findIdByMail(String email) throws ServiceException;
}
