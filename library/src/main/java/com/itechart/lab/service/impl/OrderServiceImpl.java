package com.itechart.lab.service.impl;

import com.itechart.lab.model.Order;
import com.itechart.lab.model.Period;
import com.itechart.lab.model.Status;
import com.itechart.lab.repository.DaoException;
import com.itechart.lab.repository.OrderDao;
import com.itechart.lab.repository.pool.ConnectionWrapper;
import com.itechart.lab.service.OrderService;
import com.itechart.lab.service.PeriodCalculator;
import com.itechart.lab.service.ServiceException;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl  implements OrderService {
    private OrderDao orderDao;

    private OrderServiceImpl() {
        orderDao = OrderDao.getInstance();
    }

    public static OrderServiceImpl getInstance() {
        return OrderServiceHolder.ORDER_SERVICE;
    }

    public boolean createOrder(int bookId, int readerId,String status, Period period,
                               String comment) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
           Connection connection = connectionWrapper.getConnection();
           Order order = new Order();
           orderDao.updateAmountWhenBorrowing(connection, bookId);
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
           return orderDao.insert(connection, order);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while creating order.", exception);
        }

    }

    @Override
    public boolean closeOrder(int id, Status status, Date returnDate) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            Connection connection = connectionWrapper.getConnection();
            Order order = orderDao.selectEntityById(connection, id);
            order.setStatus(status);
            order.setReturnDate((java.sql.Date) returnDate);
            orderDao.updateAmountWhenReturning(connection, order.getBookId());
            return orderDao.update(connection, order);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while closing order record.", exception);
        }
    }

    @Override
    public List<Order> findBookOrderers(int bookId) throws ServiceException{
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return orderDao.selectBookOrders(connectionWrapper.getConnection(), bookId);
        }
        catch (DaoException exception) {
            throw new ServiceException("Exception while finding order.", exception);
        }
    }

    public Order findOrder(int id) throws ServiceException{
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return orderDao.selectEntityById(connectionWrapper.getConnection(), id);
        }
        catch (DaoException exception) {
            throw new ServiceException("Exception while finding order.", exception);
        }
    }

    @Override
    public boolean editOrder(int orderId,Status status) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            Connection connection = connectionWrapper.getConnection();
            Order order = new Order();
            order.setId(orderId);
            Order order1 = orderDao.selectEntityById(connection, orderId);
            if (status != order1.getStatus()){
                long millis=System.currentTimeMillis();
                java.sql.Date date=new java.sql.Date(millis);
                orderDao.updateReturnDate(connection, date, orderId);
                if (status == Status.LOST || status == Status.RETURNED_AND_DAMAGED) {
                    orderDao.updateTotalAmount(connection, orderId);
                } else if (status == Status.RETURNED) {
                    orderDao.updateAmountWhenReturning(connection, orderId);
                }
            }
            return orderDao.updateStatus(connection, status, orderId);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while editing order.", exception);
        }

    }

    @Override
    public boolean saveOrder(int bookId, int readerId,  Period period,
                             String comment) throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            Order order = new Order();
            Connection connection = connectionWrapper.getConnection();
            orderDao.updateAmountWhenBorrowing(connection,bookId);
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
            return orderDao.insert(connection, order);
        } catch (DaoException exception) {
            throw new ServiceException("Exception while creating order.", exception);
        }

    }

    @Override
    public List<Order> findAll() throws ServiceException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper()) {
            return orderDao.selectAll(connectionWrapper.getConnection());
        } catch (DaoException exception) {
            throw new ServiceException("Exception during finding all books by pages operation.", exception);
        }
    }

    private static class OrderServiceHolder {
        private static final OrderServiceImpl ORDER_SERVICE = new OrderServiceImpl();
    }
}
