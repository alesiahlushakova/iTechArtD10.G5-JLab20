package com.itechart.lab.model;

import java.io.InputStream;
import java.sql.Date;
import java.util.Objects;

public class Book {
    private int id;
    private InputStream cover;
    private String title;
    private String publisher;
    private Date publish_date;
    private int page_count;
    private String description;
    private int totalAmount;
    private String ISBN;
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InputStream getCover() {
        return cover;
    }

    public void setCover(InputStream cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getId() == book.getId() &&
                getPage_count() == book.getPage_count() &&
                getTotalAmount() == book.getTotalAmount() &&
                isStatus() == book.isStatus() &&
                Objects.equals(getCover(), book.getCover()) &&
                getTitle().equals(book.getTitle()) &&
                getPublisher().equals(book.getPublisher()) &&
                getPublish_date().equals(book.getPublish_date()) &&
                Objects.equals(getDescription(), book.getDescription()) &&
                getISBN().equals(book.getISBN());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCover(), getTitle(), getPublisher(), getPublish_date(), getPage_count(), getDescription(), getTotalAmount(), getISBN(), isStatus());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", cover=" + cover +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publish_date=" + publish_date +
                ", page_count=" + page_count +
                ", description='" + description + '\'' +
                ", totalAmount=" + totalAmount +
                ", ISBN='" + ISBN + '\'' +
                ", status=" + status +
                '}';
    }
}
