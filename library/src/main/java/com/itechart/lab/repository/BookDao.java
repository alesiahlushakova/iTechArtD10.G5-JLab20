package com.itechart.lab.repository;

import com.itechart.lab.model.Book;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDao extends AbstractDao<Book> {
    private static final String SELECT_ALL_QUERY = "SELECT * FROM book";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM book WHERE id=?";
    private static final String SELECT_BY_ISBN_AND_TITLE = "SELECT id FROM book WHERE isbn=? AND title=? limit 1";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM book WHERE id=?";
    private static final String RELATE_WITH_AUTHORS_QUERY = "INSERT INTO book_author (book_id, author_id) VALUES (?,?)";
    private static final String RELATE_WITH_GENRES_QUERY = "INSERT INTO book_genre (book_id, genre_id) VALUES (?,?)";
    private static final String INSERT_ENTITY_QUERY =
            "INSERT INTO book (cover,title,publisher,publish_date,page_count,description,total_amount,remaining_amount,ISBN,status) VALUES(?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY =
            "UPDATE book SET cover=?, title=?, publisher=?, publish_date=?, page_count=?, description=?, total_amount=?,remaining_amount=?, ISBN=?, status=? WHERE id=?";
    private static final String SELECT_AMOUNT_OF_AVAILABLE_BOOKS_QUERY =
            "SELECT remaining_amount FROM book WHERE status=true AND id=?";
    private static final String UPDATE_WHEN_BORROWING_QUERY =
            "UPDATE book SET remaining_amount=total_amount-1 WHERE id=?";
    private static final String UPDATE_WHEN_RETURNING_QUERY =
            "UPDATE book SET remaining_amount=total_amount+1 WHERE id=?";
    private static final String SEARCH_QUERY =
            "SELECT  DISTINCT book.id,cover,title,publisher,publish_date,page_count,description,total_amount,remaining_amount,ISBN,status\n" +
            "             FROM book \n" +
            "             INNER JOIN book_genre ON book.id=book_id\n" +
            "             INNER JOIN book_author ba on book.id = ba.book_id\n" +
            "             INNER JOIN author a on ba.author_id = a.id\n" +
            "             INNER JOIN genre ON genre.id=genre_id\n";
    private static final String SELECT_IMAGE_BY_USER_ID_QUERY = "SELECT cover FROM book WHERE id=?";
    private static final String SELECT_BOOK_AUTHORS_QUERY = "\n" +
                                                            "SELECT DISTINCT name FROM book_author\n" +
                                                            "            INNER JOIN author ON author_id = id\n" +
                                                            "            WHERE book_id=?";
    private static final String SELECT_BOOK_GENRES_QUERY = "\n" +
                                                           "SELECT DISTINCT genre FROM book_genre\n" +
                                                           "            INNER JOIN genre ON genre_id = id\n" +
                                                           "            WHERE book_id=?";
    private static final String UPDATE_STATUS_QUERY = "UPDATE book SET status=? WHERE id=?";
    private static final String SELECT_BY_FOUND_ROWS_QUERY = "SELECT SQL_CALC_FOUND_ROWS * FROM book LIMIT %d, %d";
    private static final String SELECT_BY_FOUND_ROWS_FILTERED_QUERY =
            "SELECT SQL_CALC_FOUND_ROWS * FROM book WHERE status=1 LIMIT %d, %d";
    private static final String SELECT_FOUND_ROWS_QUERY = "SELECT COUNT(*) from book";
    private static final String SELECT_FOUND_ROWS_FILTERED_QUERY = "SELECT COUNT(*) from book WHERE status=1";
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
    private int numberOfRecords;

    private BookDao() {

    }

    public static BookDao getInstance() {
        return BookDaoHolder.BOOK_DAO;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public List<String> getBookAuthors(Connection connection, int id) throws DaoException {
        try (PreparedStatement statement = prepareStatementForQuery(connection, SELECT_BOOK_AUTHORS_QUERY, id)) {
            ResultSet resultSet = statement.executeQuery();
            List<String> authorList = new ArrayList<>();
            while (resultSet.next()) {
                String firstname = resultSet.getString(1);
                authorList.add(firstname);
            }
            return authorList;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    public int insertBook(Connection connection, InputStream cover, String title, String publisher,
                          java.util.Date publishDate, int pageCount, String description,
                          int totalAmount, String isbn, int status)
            throws DaoException {
        int id = 0;
        try (PreparedStatement preparedStatement = prepareStatementForQuery(connection,
                                                                            INSERT_ENTITY_QUERY, cover, title,
                                                                            publisher, publishDate, pageCount,
                                                                            description, totalAmount, totalAmount, isbn,
                                                                            status)) {
            boolean isSuccess = preparedStatement.execute();
            if (!isSuccess) {
                try (PreparedStatement preparedStatement1 = prepareStatementForQuery(connection,
                                                                                     SELECT_BY_ISBN_AND_TITLE, isbn,
                                                                                     title)) {
                    ResultSet resultSet = preparedStatement1.executeQuery();
                    if (resultSet.next()) {
                        id = resultSet.getInt(1);
                    }
                    return id;
                } catch (SQLException exception) {
                    throw new DaoException(exception.getMessage(), exception);
                }
            }
            return 0;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    public List<String> getBookGenres(Connection connection, int id) throws DaoException {
        try (PreparedStatement statement = prepareStatementForQuery(connection, SELECT_BOOK_GENRES_QUERY, id)) {
            ResultSet resultSet = statement.executeQuery();
            List<String> authorList = new ArrayList<>();
            String genre;
            while (resultSet.next()) {
                genre = resultSet.getString(1);

                authorList.add(genre);
            }

            return authorList;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    public List<Book> searchForBookByCriteria(Connection connection, String description,
                                              String title, List<String> genres,
                                              List<String> authors) throws DaoException {
        StringBuilder sqlQuery = new StringBuilder(SEARCH_QUERY);
        sqlQuery.append("WHERE (title='").append(title).append("' OR '").append(title).
                append("' = '' ) AND (description='").append(description).append("' OR '").append(description)
                .append("' = ''  ) ");
        if(!genres.isEmpty() && genres.get(0) != null){
            sqlQuery.append("AND  (genre IN ( '");
            for( int i=0; i<genres.size();i++){
                if(i==(genres.size()-1)){
                    sqlQuery.append(genres.get(i)).append("'))");
                } else {
                    sqlQuery.append(genres.get(i)).append("', '");
                }
            }
        }
        if(!authors.isEmpty() && authors.get(0) != null){
            sqlQuery.append("AND  (a.name IN ( '");
            for( int i=0; i<authors.size();i++){
                if(i==(authors.size()-1)){
                    sqlQuery.append(authors.get(i)).append("'))");
                } else {
                    sqlQuery.append(authors.get(i)).append("', '");
                }
            }
        }


        try (Statement preparedStatement
                     = connection.createStatement()) {
            ResultSet resultSet = preparedStatement.executeQuery(String.valueOf(sqlQuery));
            List<Book> bookList = new ArrayList<>();
            Book book;
            while (resultSet.next()) {
                book = buildEntity(connection, resultSet);
                bookList.add(book);
            }
            return bookList;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }


    public boolean updateStatus(Connection connection, boolean status, int id) throws DaoException {
        return executeQuery(connection, UPDATE_STATUS_QUERY, status, id);
    }

    public int getAmountOfAvailableBooks(Connection connection, boolean status, int id) throws DaoException {
        try (PreparedStatement statement = prepareStatementForQuery(connection, SELECT_AMOUNT_OF_AVAILABLE_BOOKS_QUERY,
                                                                    status, id)) {
            ResultSet resultSet = statement.executeQuery();
            int amount = resultSet.getInt(1);
            return amount;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    public boolean relateWithGenres(Connection connection, int bookId, List<Integer> genres) throws DaoException {
        boolean isRelated = false;
        for (Integer genre :
                genres) {
            isRelated = false;
            if (genre == null) {
                throw new DaoException("Error while creating relation");
            }
            try (PreparedStatement statement = prepareStatementForQuery(connection, RELATE_WITH_GENRES_QUERY, bookId,
                                                                        genre)) {
                boolean isSuccess = statement.execute();
                if (!isSuccess) {
                    isRelated = true;
                }

            } catch (SQLException exception) {
                throw new DaoException("Error while creating relation", exception);
            }
        }
        return isRelated;
    }

    public boolean relateWithAuthors(Connection connection, int bookId, List<Integer> authors) throws DaoException {
        boolean isRelated = false;
        for (Integer author :
                authors) {
            isRelated = false;
            if (author == null) {
                throw new DaoException("Error while creating relation");
            }
            try (PreparedStatement statement = prepareStatementForQuery(connection, RELATE_WITH_AUTHORS_QUERY, bookId,
                                                                        author)) {
                boolean isSuccess = statement.execute();
                if (!isSuccess) {
                    isRelated = true;
                }

            } catch (SQLException exception) {
                throw new DaoException("Error while creating relation", exception);
            }
        }
        return isRelated;
    }

    public byte[] selectImageById(Connection connection, int userId)
            throws DaoException {

        try (PreparedStatement preparedStatement
                     = prepareStatementForQuery(connection, SELECT_IMAGE_BY_USER_ID_QUERY,
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


    public List<Book> selectAllBooks(Connection connection, int offSet, int numberOfRecords) throws DaoException {
        try (Statement statement = connection.createStatement()) {
            String sqlQuery = String.format(SELECT_BY_FOUND_ROWS_QUERY, offSet, numberOfRecords);
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            List<Book> findClients = new ArrayList<>();
            while (resultSet.next()) {
                Book book = buildEntity(connection, resultSet);
                findClients.add(book);
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

    public List<Book> selectAllAvailableBooks(Connection connection, int offSet, int numberOfRecords)
            throws DaoException {
        try (Statement statement = connection.createStatement()) {
            String sqlQuery = String.format(SELECT_BY_FOUND_ROWS_FILTERED_QUERY,
                                            offSet, numberOfRecords);
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            List<Book> findClients = new ArrayList<>();
            while (resultSet.next()) {
                Book book = buildEntity(connection, resultSet);
                findClients.add(book);
            }

            resultSet = statement.executeQuery(SELECT_FOUND_ROWS_FILTERED_QUERY);
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


        InputStream cover = entity.getCoverImage();
        if (cover == null) {
            parameters.add(NULL_PARAMETER);
        } else {
            String text = null;
            try {
                text = IOUtils.toString(cover, StandardCharsets.UTF_8.name());
            } catch (IOException e) {
                e.printStackTrace();
            }
            parameters.add(String.valueOf(text));
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

        int status = entity.getStatus();
        String statusValue = String.valueOf(status);
        parameters.add(statusValue);

        return parameters;
    }

    @Override
    protected Book buildEntity(Connection connection, ResultSet resultSet) throws DaoException {
        try {
            Book book = new Book();

            int id = resultSet.getInt(ID_COLUMN);
            book.setId(id);
            List<String> authorList = getBookAuthors(connection, id);
            book.setAuthors(authorList);

            List<String> genreList = getBookGenres(connection, id);
            book.setGenres(genreList);

            String coverValueBlob = resultSet.getString(COVER_COLUMN);
            if (coverValueBlob != null) {
                book.setCover(coverValueBlob);
            }

            String title = resultSet.getString(TITLE_COLUMN);
            book.setTitle(title);

            String publisher = resultSet.getString(PUBLISHER_COLUMN);
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

            int status = resultSet.getInt(STATUS_COLUMN);
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

    private static class BookDaoHolder {
        private static final BookDao BOOK_DAO = new BookDao();
    }
}
