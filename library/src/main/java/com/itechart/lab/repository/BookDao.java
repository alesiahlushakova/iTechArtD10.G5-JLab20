package com.itechart.lab.repository;

import com.itechart.lab.model.Book;
import org.apache.commons.lang3.ArrayUtils;


import java.io.InputStream;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BookDao extends AbstractDao<Book>{
    private static final String SELECT_ALL_QUERY = "SELECT * FROM book";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM book WHERE id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM book WHERE id=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO book (cover,title,publisher,publish_date,page_count,description,total_amount,remaining_amount,ISBN,status) VALUES(?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE book SET title=?, publisher=?, publish_date=?, page_count=?, description=?, total_amount=?, ISBN=?, status=? WHERE ExerciseID=?";
    private static final String SELECT_AMOUNT_OF_AVAILABLE_BOOKS_QUERY = "SELECT remaining_amount FROM book WHERE status=true AND id=?";
    private static final String UPDATE_WHEN_BORROWING_QUERY = "UPDATE book SET remaining_amount=total_amount-1 WHERE id=?";
    private static final String UPDATE_WHEN_RETURNING_QUERY = "UPDATE book SET remaining_amount=total_amount+1 WHERE id=?";
    private static final String SEARCH_QUERY = "SELECT  DISTINCT book.id,cover,title,publisher,publish_date,page_count,description,total_amount,ISBN,status\n" +
            "             FROM book \n" +
            "             INNER JOIN book_genre ON book.id=book_id\n" +
            "             INNER JOIN book_author ba on book.id = ba.book_id\n" +
            "             INNER JOIN author a on ba.author_id = a.id\n" +
            "             INNER JOIN genre ON genre.id=genre_id\n" +
            "             WHERE (title=? OR ? IS NULL ) AND\n" +
            "                   (description=? OR ? IS NULL ) AND\n" +
            "                   (genre=? OR ? IS NULL ) AND\n" +
            "                   (a.last_name=? OR ? IS NULL )";
    private static final String SELECT_IMAGE_BY_USER_ID_QUERY = "SELECT cover FROM book WHERE id=?";
    private static final String SELECT_BOOK_AUTHORS_QUERY = "SELECT first_name, last_name FROM book_author" +
            "INNER JOIN author ON book_author.author_id = author.id" +
            "WHERE book_author.book_id=?";
    private static final String UPDATE_STATUS_QUERY = "UPDATE book SET status=? WHERE id=?";
    private static final String SELECT_BY_FOUND_ROWS_QUERY = "SELECT SQL_CALC_FOUND_ROWS * FROM book LIMIT %d, %d";
    private static final String SELECT_BY_FOUND_ROWS_FILTERED_QUERY = "SELECT SQL_CALC_FOUND_ROWS * FROM book WHERE status=true LIMIT %d, %d";
    private static final String SELECT_FOUND_ROWS_QUERY = "SELECT FOUND_ROWS()";
    private static final String ID_COLUMN = "id";
    private static final String COVER_COLUMN = "cover";
    private static final String TITLE_COLUMN = "title";
    private static final String PUBLISHER_COLUMN = "publisher";
    private static final String PUBLISH_DATE_COLUMN = "publish_date";
    private static final String PAGE_COUNT_COLUMN = "page_count";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String TOTAL_AMOUNT_COLUMN = "total_amount";
    private static final String REMAINING_AMOUNT_COLUMN = "remaining_amount";
    private static final String ISBN_COLUMN = "ISBN";
    private static final String STATUS_COLUMN = "status";

    public BookDao(Connection connection) {
        super(connection);
    }
    private int numberOfRecords;
    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public List<String> getBookAuthors(int id) throws DaoException{
        try(PreparedStatement statement = prepareStatementForQuery(SELECT_BOOK_AUTHORS_QUERY, id)){
            ResultSet resultSet = statement.executeQuery();
            List<String> authorList = new ArrayList<>();
            String author = null;
            while (resultSet.next()){
                String firstname = resultSet.getString(1);
                String lastname = resultSet.getString(2);
                author=firstname+" "+lastname;
                authorList.add(author);
            }

            return authorList;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    public List<Book> searchForBookByCriteria (String description,
                                               String title, List<String> genres,
                                               List<String> authors) throws DaoException{

            StringBuilder sqlQuery = new StringBuilder(SEARCH_QUERY);

            for (String genre:
                 genres) {
                sqlQuery.append("AND  (genre=").append(genre).append(" OR ").append(genre).append(" IS NULL )");
            }
            for (String author:
            authors){
                sqlQuery.append("AND  (a.last_name=").append(author).append(" OR ").append(author).append(" IS NULL )");
            }
            try(PreparedStatement preparedStatement
                    =prepareStatementForQuery(String.valueOf(sqlQuery),description,title)){
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Book> bookList = new ArrayList<>();
                Book book = null;
                while (resultSet.next()) {
                    book = buildEntity(resultSet);
                    bookList.add(book);
                }
                return bookList;
        }catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }



    public boolean updateStatus(boolean status, int id) throws DaoException{
        return executeQuery(UPDATE_STATUS_QUERY,status,id);
    }

    public int getAmountOfAvailableBooks(boolean status, int id) throws DaoException {
        try(PreparedStatement statement = prepareStatementForQuery(SELECT_AMOUNT_OF_AVAILABLE_BOOKS_QUERY,status, id)){
           ResultSet resultSet = statement.executeQuery();
           int amount = resultSet.getInt(1);
           return amount;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }


    public byte[] selectImageById (int userId)
            throws DaoException {

        try (PreparedStatement preparedStatement
                     = prepareStatementForQuery(SELECT_IMAGE_BY_USER_ID_QUERY,
                userId)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                byte barray[] = rs.getBytes(1);

                return barray;

            }
            return null;

        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }

    }


    public List<Book> selectAllBooks(int offSet, int numberOfRecords) throws DaoException {
        try (Statement statement = connection.createStatement()) {
            String sqlQuery = String.format(SELECT_BY_FOUND_ROWS_QUERY,
                    offSet, numberOfRecords);
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            List<Book> findClients = new ArrayList<>();
            while (resultSet.next()) {
                Book user = buildEntity(resultSet);

                findClients.add(user);
            }

            resultSet = statement.executeQuery(SELECT_FOUND_ROWS_QUERY);
            if (resultSet.next()) {
                this.numberOfRecords = resultSet.getInt(1);
            }

            return findClients;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    public List<Book> selectAllAvailableBooks(int offSet, int numberOfRecords) throws DaoException {
        try (Statement statement = connection.createStatement()) {
            String sqlQuery = String.format(SELECT_BY_FOUND_ROWS_FILTERED_QUERY,
                    offSet, numberOfRecords);
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            List<Book> findClients = new ArrayList<>();
            while (resultSet.next()) {
                Book user = buildEntity(resultSet);

                findClients.add(user);
            }

            resultSet = statement.executeQuery(SELECT_FOUND_ROWS_QUERY);
            if (resultSet.next()) {
                this.numberOfRecords = resultSet.getInt(1);
            }

            return findClients;
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

        InputStream cover = entity.getInputStream();
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

        int remainingAmount = entity.getRemainingAmount();
        String remainingAmountValue = String.valueOf(remainingAmount);
        parameters.add(remainingAmountValue);

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
            byte[] coverValue =  coverValueBlob.getBytes(1,(int)coverValueBlob.length());
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

            int remainingAmount = resultSet.getInt(REMAINING_AMOUNT_COLUMN);
            book.setRemainingAmount(remainingAmount);

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
