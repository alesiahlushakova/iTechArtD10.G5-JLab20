package com.itechart.lab.repository;

import com.itechart.lab.model.Book;
import org.apache.commons.lang3.ArrayUtils;


import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BookDao extends AbstractDao<Book>{
    private static final String SELECT_ALL_QUERY = "SELECT * FROM book";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM book WHERE id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM book WHERE id=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO book (cover,title,publisher,publish_date,page_count,description,total_amount,ISBN,status) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE book SET title=?, publisher=?, publish_date=?, page_count=?, description=?, total_amount=?, ISBN=?, status=? WHERE ExerciseID=?";
    //private static final String SELECT_FOR_BOOK_LIST_QUERY = "SELECT title, author, publish_date,"
    private static final String SELECT_AMOUNT_OF_AVAILABLE_BOOKS_QUERY = "SELECT count(*) FROM book WHERE status=true";
    private static final String SELECT_BY_TITLE = "SELECT * FROM book WHERE title=?";
    private static final String SELECT_BY_DESCRIPTION = "SELECT * FROM book WHERE description=?";
    private static final String SELECT_BY_AUTHOR = "SELECT  DISTINCT book.id,cover,title,publisher,publish_date,page_count,description,total_amount,ISBN,status" +
            " FROM book INNER JOIN book_author ON book.id=book_id" +
            "INNER JOIN author ON author.id=author_id WHERE author=?";
    private static final String SELECT_BY_GENRE = "SELECT  DISTINCT book.id,cover,title,publisher,publish_date,page_count,description,total_amount,ISBN,status" +
            " FROM book INNER JOIN book_genre ON book.id=book_id" +
            "INNER JOIN genre ON genre.id=genre_id WHERE genre=?";

    private static final String ID_COLUMN = "id";
    private static final String COVER_COLUMN = "cover";
    private static final String TITLE_COLUMN = "title";
    private static final String PUBLISHER_COLUMN = "publisher";
    private static final String PUBLISH_DATE_COLUMN = "publish_date";
    private static final String PAGE_COUNT_COLUMN = "page_count";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String TOTAL_AMOUNT_COLUMN = "total_amount";
    private static final String ISBN_COLUMN = "ISBN";
    private static final String STATUS_COLUMN = "status";

    public BookDao(Connection connection) {
        super(connection);
    }

    public int getAmountOfAvailableBooks(Book book) throws DaoException {
        try(Statement statement = connection.createStatement()){
           ResultSet resultSet = statement.executeQuery(SELECT_AMOUNT_OF_AVAILABLE_BOOKS_QUERY);
           int amount = resultSet.getInt(1);
           return amount;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    @Override
    protected List<String> getEntityParameters(Book entity) {
        List<String> parameters = new ArrayList<>();

        int id = entity.getId();
        String idValue = String.valueOf(id);
        parameters.add(idValue);

        Byte[] cover = entity.getCover();
        if(cover == null) {
            parameters.add(NULL_PARAMETER);
        } else {
            String coverValue = String.valueOf(cover);
            parameters.add(coverValue);
        }

        String title = entity.getTitle();
        parameters.add(title);

        String publisher = entity.getPublisher();
        parameters.add(publisher);

        Date publishDate = entity.getPublishDate();
        String publishDateValue = String.valueOf(publishDate);
        parameters.add(publishDateValue);

        int pageCount = entity.getPageCount();
        String pageCountValue = String.valueOf(pageCount);
        parameters.add(pageCountValue);

        String description = entity.getDescription();
        parameters.add(description);

        int totalAmount = entity.getTotalAmount();
        String totalAmountValue = String.valueOf(totalAmount);
        parameters.add(totalAmountValue);

        String isbn = entity.getISBN();
        parameters.add(isbn);

        boolean status = entity.getStatus();
        String statusValue = String.valueOf(status);
        parameters.add(statusValue);

        return parameters;
    }

    @Override
    protected Book buildEntity(ResultSet resultSet) throws DaoException {
        try {
            Book book = new Book();

            int id = resultSet.getInt(ID_COLUMN);
            book.setId(id);

            Blob coverValueBlob = resultSet.getBlob(COVER_COLUMN);
            byte coverValue[] =  coverValueBlob.getBytes(1,(int)coverValueBlob.length());
            Byte[] cover = ArrayUtils.toObject(coverValue);
            book.setCover(cover);

            String title = resultSet.getString(TITLE_COLUMN);
            book.setTitle(title);

            String publisher  = resultSet.getString(PUBLISHER_COLUMN);
            book.setPublisher(publisher);

            Date publishDate = resultSet.getDate(PUBLISH_DATE_COLUMN);
            book.setPublishDate(publishDate);

            int pageCount = resultSet.getInt(PAGE_COUNT_COLUMN);
            book.setPageCount(pageCount);

            String description = resultSet.getString(DESCRIPTION_COLUMN);
            book.setDescription(description);

            int totalAmount = resultSet.getInt(TOTAL_AMOUNT_COLUMN);
            book.setTotalAmount(totalAmount);

            String isbn = resultSet.getString(ISBN_COLUMN);
            book.setISBN(isbn);

            boolean status = resultSet.getBoolean(STATUS_COLUMN);
            book.setStatus(status);

            return book;
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
