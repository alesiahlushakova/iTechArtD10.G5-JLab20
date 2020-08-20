package com.itechart.lab.repository;

public class BookDao {
    private static final String SELECT_ALL_QUERY = "SELECT * FROM book";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM book WHERE id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM book WHERE id=?";
    private static final String INSERT_ENTITY_QUERY = "INSERT INTO book (title,publisher,publish_date,page_count,description,total_amount,ISBN,status) VALUES(?,?,?,?,?,?,?,?)";
    private static final String UPDATE_ENTITY_QUERY = "UPDATE book SET title=?, publisher=?, publish_date=?, page_count=?, description=?, total_amount=?, ISBN=?, status=? WHERE ExerciseID=?";
  //  private static final String SELECT_FOR_BOOK_LIST_QUERY = "SELECT title, author, publish_date,"
}
