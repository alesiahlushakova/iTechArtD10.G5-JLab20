package com.itechart.lab.model;

import java.sql.Date;
import java.util.Objects;

public class Order implements Entity{
    private int id;
    private int bookId;
    private int readerId;
    private Date borrowDate;
    private Period period;
    private Status status;
    private String comment;
    private Date dueDate;
    private Reader reader;
    private Book book;
    private Date returnDate;


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId() == order.getId() &&
                getBookId() == order.getBookId() &&
                getReaderId() == order.getReaderId() &&
                getPeriod() == order.getPeriod() &&
                getBorrowDate().equals(order.getBorrowDate()) &&
                getStatus().equals(order.getStatus()) &&
                Objects.equals(getComment(), order.getComment()) &&
                getDueDate().equals(order.getDueDate()) &&
                Objects.equals(getReturnDate(), order.getReturnDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBorrowDate(), getStatus(), getComment(), getDueDate(), getReturnDate());
    }
}
