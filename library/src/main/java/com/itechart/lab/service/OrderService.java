package com.itechart.lab.service;

import com.itechart.lab.model.Order;
import com.itechart.lab.model.Period;
import com.itechart.lab.model.Status;

import java.util.Date;
import java.util.List;

public interface OrderService {
    boolean closeOrder(int id, Status status, Date returnDate) throws ServiceException;

    List<Order> findBookOrderers(int bookId) throws ServiceException;

    boolean editOrder(int orderId, Status status) throws ServiceException;

    boolean saveOrder(int bookId, int readerId, Period period,
                      String comment) throws ServiceException;

    List<Order> findAll() throws ServiceException;
}
