package com.itechart.lab.model;

import java.sql.Date;
import java.util.Objects;

public class Order extends Entity{
    private int id;
    private Date borrowDate;
    private Status status;
    private String comment;
    private Date dueDate;
    private Date returnDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId() == order.getId() &&
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
