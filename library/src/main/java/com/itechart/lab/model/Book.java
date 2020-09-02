package com.itechart.lab.model;

import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Book extends Entity {
    private int id;
    private Byte[] cover;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private InputStream inputStream;
    private String title;
    private String publisher;
    private Date publish_date;
    private int page_count;
    private String description;
    private int totalAmount;

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    private List<String> authors;
    private int remainingAmount;
    private String ISBN;
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Byte[] getCover() {
        return cover;
    }

    public void setCover(Byte[] cover) {
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

    public Date getPublishDate() {
        return publish_date;
    }

    public void setPublishDate(Date publish_date) {
        this.publish_date = publish_date;
    }

    public int getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(int remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public int getPageCount() {
        return page_count;
    }

    public void setPageCount(int page_count) {
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

    public boolean getStatus() {
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
                getPageCount() == book.getPageCount() &&
                getTotalAmount() == book.getTotalAmount() &&
                getStatus() == book.getStatus() &&
                Objects.equals(getCover(), book.getCover()) &&
                getTitle().equals(book.getTitle()) &&
                getPublisher().equals(book.getPublisher()) &&
                getPublishDate().equals(book.getPublishDate()) &&
                Objects.equals(getDescription(), book.getDescription()) &&
                getISBN().equals(book.getISBN());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCover(), getTitle(), getPublisher(), getPublishDate(), getPageCount(), getDescription(), getTotalAmount(), getISBN(), getStatus());
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
