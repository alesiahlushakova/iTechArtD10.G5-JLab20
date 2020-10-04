package com.itechart.lab.service;

import com.itechart.lab.model.Order;
import com.itechart.lab.model.Period;
import com.itechart.lab.model.Status;

import java.util.Date;
import java.util.List;

public interface OrderService {
    /**
     * method closes book order
     * @param id order id
     * @param status status
     * @param returnDate return date
     * @return is closed
     * @throws ServiceException exception
     */
    boolean closeOrder(int id, Status status, Date returnDate) throws ServiceException;

    /**
     * method finds ones who ordered book
     * @param bookId book id
     * @return list of orders
     * @throws ServiceException exception
     */
    List<Order> findBookOrderers(int bookId) throws ServiceException;

    /**
     * method edits order
     * @param orderId order id
     * @param status status
     * @return is edited
     * @throws ServiceException exception
     */
    boolean editOrder(int orderId, Status status) throws ServiceException;

    /**
     * methos saves order
     * @param bookId book id
     * @param readerId reader id
     * @param period borrow period
     * @param comment comment
     * @return is saved
     * @throws ServiceException exception
     */
    boolean saveOrder(int bookId, int readerId, Period period,
                      String comment) throws ServiceException;

    /**
     * methos finds all orders
     * @return orders
     * @throws ServiceException exception
     */
    List<Order> findAll() throws ServiceException;
}
