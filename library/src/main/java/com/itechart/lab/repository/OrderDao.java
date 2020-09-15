package com.itechart.lab.repository;

import com.itechart.lab.model.Order;
import com.itechart.lab.model.Period;
import com.itechart.lab.model.Reader;
import com.itechart.lab.model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDao extends AbstractDao<Order> {
    /**
     * Common queries.
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM book_order";
    private static final String SELECT_ALL_ABOUT_READER_QUERY = "SELECT * FROM reader WHERE id=?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM book_order WHERE id=?";
    private static final String UPDATE_TOTAL_AMOUNT_QUERY = "UPDATE book_order SET total_amount=total_amount-1 WHERE id=?";
    private static final String SELECT_BY_BOOK_ID_QUERY = "SELECT * FROM book_order WHERE book_id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM book_order WHERE id=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO book_order (`book_id`,`reader_id`,`borrow_date`,`borrow_period`,`status`,`comment`,`due_date`,`return_date`)  VALUES(?,?,?,?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE book_order SET  book_id=?, reader_id=?, borrow_date=?,`borrow_period`=?, status=?, comment=?, due_date=?, return_date=? WHERE id=?";
    private static final String UPDATE_STATUS_ENTITY_QUERY = "UPDATE book_order SET  status=? WHERE id=?";
    private static final String SELECT_BOOK_AVAILABILITY_DATE_QUERY = "SELECT due_date from book_order " +
            "WHERE return_date IS NULL AND book_id=? " +
            "ORDER BY due_date " +
            "LIMIT 1";
    private static final String SELECT_RECORD_QUERY = "SELECT email, first_name, borrow_date, borrow_period, status, comment" +
            "FROM book_order INNER JOIN reader" +
            "ON reader_id=reader.id" +
            "WHERE book_id=? ";
    private static final String UPDATE_WHEN_BORROWING_QUERY = "UPDATE book SET remaining_amount=total_amount-1 WHERE id=?";
    private static final String UPDATE_WHEN_RETURNING_QUERY = "UPDATE book SET remaining_amount=total_amount+1 WHERE id=?";
    private static final String ID_COLUMN = "id";
    private static final String BOOK_ID_COLUMN = "book_id";
    private static final String READER_ID_COLUMN = "reader_id";
    private static final String BORROW_DATE_COLUMN = "borrow_date";
    private static final String STATUS_COLUMN = "status";
    private static final String PERIOD_COLUMN = "borrow_period";
    private static final String COMMENT_COLUMN = "comment";
    private static final String DUE_DATE_COLUMN = "due_date";
    private static final String RETURN_DATE_COLUMN = "return_date";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String EMAIL_COLUMN = "email";
    private static final String GENDER_COLUMN = "gender";
    private static final String PHONE_COLUMN = "phone";
    private static final String REGISTRATION_DATE_COLUMN = "date_of_registration";


    public OrderDao(Connection connection){
        super(connection);
    }

    public Date getAvailabilityDate(int bookId) throws DaoException{
        try (PreparedStatement preparedStatement
                = prepareStatementForQuery(SELECT_BOOK_AVAILABILITY_DATE_QUERY,
                bookId)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Date availabilityDate = null;
            if (resultSet.next()) {
                availabilityDate = resultSet.getDate(DUE_DATE_COLUMN);
            }
            return availabilityDate;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    public  List<Order> selectBookOrders(int id) throws DaoException{
        try(PreparedStatement statement = prepareStatementForQuery(SELECT_BY_BOOK_ID_QUERY, id)){
            ResultSet resultSet = statement.executeQuery();
            Order order = new Order();
            ArrayList<Order> orders = new ArrayList<>();
            while (resultSet.next()){
                order = buildEntity(resultSet);
                orders.add(order);
            }

            return orders;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }


    public  Reader getReader(int id) throws DaoException{
        try(PreparedStatement statement = prepareStatementForQuery(SELECT_ALL_ABOUT_READER_QUERY, id)){
            ResultSet resultSet = statement.executeQuery();
            Reader reader = new Reader();
            while (resultSet.next()){
               reader = buildReaderEntity(resultSet);
            }

            return reader;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    @Override
    protected List<String> getEntityParameters(Order entity) {
        List<String> parameters = new ArrayList<>();

        int bookId = entity.getBookId();
        String bookIdValue = String.valueOf(bookId);
        parameters.add(bookIdValue);

        int readerId = entity.getReaderId();
        String readerIdValue = String.valueOf(readerId);
        parameters.add(readerIdValue);

        Date borrowDate = entity.getBorrowDate();
        String borrowDateValue = String.valueOf(borrowDate);
        parameters.add(borrowDateValue);

        Period period = entity.getPeriod();
        String periodValue = String.valueOf(period);
        parameters.add(periodValue);

        Status status = entity.getStatus();
        String statusValue = String.valueOf(status);
        parameters.add(statusValue);

        String comment = entity.getComment();
        if (comment == null) {
            parameters.add(NULL_PARAMETER);
        } else {
            parameters.add(comment);
        }

        Date dueDate = entity.getDueDate();
        String dueDateValue = String.valueOf(dueDate);
        parameters.add(dueDateValue);

        Date returnDate = entity.getReturnDate();
        if (returnDate == null) {
            parameters.add(NULL_PARAMETER);
        } else {
            parameters.add(String.valueOf(returnDate));
        }

        return parameters;
    }

    public boolean updateStatus(Status status, int id) throws DaoException{
        return executeQuery(UPDATE_STATUS_ENTITY_QUERY,String.valueOf(status),id);
    }


    public boolean updateAmountWhenBorrowing(int id) throws DaoException{
        return executeQuery(UPDATE_WHEN_BORROWING_QUERY,id);
    }

    public boolean updateAmountWhenReturning( int id) throws DaoException{
        return executeQuery(UPDATE_WHEN_RETURNING_QUERY,id);
    }

    public boolean updateTotalAmount( int id) throws DaoException{
        return executeQuery(UPDATE_TOTAL_AMOUNT_QUERY,id);
    }

    @Override
    protected Order buildEntity(ResultSet resultSet) throws DaoException {
        try {
           Order order = new Order();

           int id = resultSet.getInt(ID_COLUMN);
           order.setId(id);

           int bookId = resultSet.getInt(BOOK_ID_COLUMN);
           order.setBookId(bookId);

           int readerId = resultSet.getInt(READER_ID_COLUMN);
           order.setReaderId(readerId);
           order.setReader(getReader(readerId));

           Date borrowDate = resultSet.getDate(BORROW_DATE_COLUMN);
           order.setBorrowDate(borrowDate);

            String periodValue = resultSet.getString(PERIOD_COLUMN);
            Period period = Period.valueOf(periodValue);
            order.setPeriod(period);

           String statusValue = resultSet.getString(STATUS_COLUMN);
           Status status = Status.valueOf(statusValue);
           order.setStatus(status);

           String comment = resultSet.getString(COMMENT_COLUMN);
           order.setComment(comment);

           Date dueDate = resultSet.getDate(DUE_DATE_COLUMN);
           order.setDueDate(dueDate);

           Date returnDate = resultSet.getDate(RETURN_DATE_COLUMN);
           order.setReturnDate(returnDate);

           return order;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
        }

    protected Reader buildReaderEntity(ResultSet resultSet) throws DaoException {
        try {
            Reader reader = new Reader();

            int id = resultSet.getInt(ID_COLUMN);
            reader.setId(id);

            String firstname = resultSet.getString(FIRST_NAME_COLUMN);
            reader.setFirstname(firstname);

            String lastname = resultSet.getString(LAST_NAME_COLUMN);
            reader.setLastname(lastname);

            String email = resultSet.getString(EMAIL_COLUMN);
            reader.setEmail(email);

            int gender = resultSet.getInt(GENDER_COLUMN);
            reader.setGender(gender);

            String phone = resultSet.getString(PHONE_COLUMN);
            reader.setPhone(phone);

            Date registrationDate = resultSet.getDate(REGISTRATION_DATE_COLUMN);
            reader.setDateOfRegistration((java.sql.Date) registrationDate);

            return reader;

        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    @Override
    protected Map<String, String> initializeCommonQueries() {
        Map<String, String> commonQueries = new HashMap<>();
        commonQueries.put(SELECT_ALL_QUERY_KEY, SELECT_ALL_QUERY);
        commonQueries.put(SELECT_BY_ID_QUERY_KEY, SELECT_BY_ID_QUERY);
        commonQueries.put(DELETE_BY_ID_QUERY_KEY, DELETE_BY_ID_QUERY);
        commonQueries.put(INSERT_ENTITY_QUERY_KEY, INSERT_ENTITY_QUERY);
        commonQueries.put(UPDATE_ENTITY_QUERY_KEY, UPDATE_ENTITY_QUERY);

        return commonQueries;
    }

}
