package com.itechart.lab.service;

import com.itechart.lab.model.Book;
import com.itechart.lab.model.Order;
import com.itechart.lab.model.Period;
import com.itechart.lab.model.Status;
import com.itechart.lab.repository.BookDao;
import com.itechart.lab.repository.DaoException;
import com.itechart.lab.repository.OrderDao;
import com.itechart.lab.repository.pool.ConnectionWrapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderService {


    public boolean createOrder(int bookId, int readerId,String status, Period period,
                               String comment) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
           OrderDao orderDao = new OrderDao(connectionWrapper.getConnection());
           Order order = new Order();
           orderDao.updateAmountWhenBorrowing(bookId);
           order.setBookId(bookId);
           order.setReaderId(readerId);
           order.setStatus(Status.ORDERED);
           order.setComment(comment);
           PeriodCalculator periodCalculator = new PeriodCalculator();
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);
           java.sql.Date dueDate = periodCalculator.calculateExpirationDate(period, date);
           order.setDueDate(dueDate);
           order.setReturnDate(null);
           return orderDao.insert(order);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while creating order.", exception);
        }

    }

    public boolean closeOrder(int id, Status status, Date returnDate) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            OrderDao orderDao = new OrderDao(connectionWrapper.getConnection());
            Order order = orderDao.selectEntityById(id);
            order.setStatus(status);
            order.setReturnDate((java.sql.Date) returnDate);
            orderDao.updateAmountWhenReturning(order.getBookId());
            return orderDao.update(order);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while closing order record.", exception);
        }
    }

    public List<Order> findBookOrderers(int bookId) throws ServiceException{
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            OrderDao orderDao = new OrderDao(connectionWrapper.getConnection());

            return orderDao.selectBookOrders(bookId);
        }
        catch (DaoException exception) {
            throw new ServiceException("Exception while finding order.", exception);
        }
    }

    public Order findOrder(int id) throws ServiceException{
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            OrderDao orderDao = new OrderDao(connectionWrapper.getConnection());

            return orderDao.selectEntityById(id);
        }
        catch (DaoException exception) {
            throw new ServiceException("Exception while finding order.", exception);
        }
    }

    public boolean editOrder(int orderId,Status status) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            OrderDao orderDao = new OrderDao(connectionWrapper.getConnection());
            Order order = new Order();
            order.setId(orderId);
            Order order1 = orderDao.selectEntityById(orderId);
            if (status != order1.getStatus()){
                if (status == Status.LOST || status == Status.RETURNED_AND_DAMAGED) {
                    orderDao.updateTotalAmount(orderId);
                } else if (status == Status.RETURNED) {
                    orderDao.updateAmountWhenReturning(orderId);
                }
            }
            return orderDao.updateStatus(status, orderId);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while editing order.", exception);
        }

    }

    public boolean saveOrder(int bookId, int readerId, Status status, Period period,
                             String comment) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            OrderDao orderDao = new OrderDao(connectionWrapper.getConnection());
            Order order = new Order();
            orderDao.updateAmountWhenBorrowing(bookId);
            order.setBookId(bookId);
            order.setReaderId(readerId);
            order.setStatus(Status.ORDERED);
            order.setPeriod(period);
            order.setComment(comment);
            PeriodCalculator periodCalculator = new PeriodCalculator();
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);
            java.sql.Date dueDate = periodCalculator.calculateExpirationDate(period, date);
            order.setDueDate(dueDate);
            order.setBorrowDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            order.setReturnDate(null);
            return orderDao.insert(order);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while creating order.", exception);
        }

    }
}
