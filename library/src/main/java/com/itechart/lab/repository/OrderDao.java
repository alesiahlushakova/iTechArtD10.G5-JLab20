package com.itechart.lab.repository;

import com.itechart.lab.model.Order;
import com.itechart.lab.model.Status;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDao extends AbstractDao<Order> {
    /**
     * Common queries.
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM book_order";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM book_order WHERE id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM book_order WHERE id=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO book_order (`book_id`,`reader_id`,`borrow_date`,`status`,`comment`,`due_date`,`return_date`)  VALUES(?,?,?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE book_order SET  book_id=?, reader_id=?, borrow_date=?, status=?, comment=?, due_date=?, return_date=? WHERE id=?";

    public static final String ID_COLUMN = "id";
    public static final String BOOK_ID_COLUMN = "book_id";
    public static final String READER_ID_COLUMN = "reader_id";
    public static final String BORROW_DATE_COLUMN = "borrow_date";
    public static final String STATUS_COLUMN = "status";
    public static final String COMMENT_COLUMN = "comment";
    public static final String DUE_DATE_COLUMN = "due_date";
    public static final String RETURN_DATE_COLUMN = "return_date";

    public OrderDao(Connection connection){
        super(connection);
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

           Date borrowDate = resultSet.getDate(BORROW_DATE_COLUMN);
           order.setBorrowDate(borrowDate);

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
