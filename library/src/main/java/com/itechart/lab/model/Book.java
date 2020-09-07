package com.itechart.lab.model;

import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Book extends Entity {
    private int id;
    private String cover;
    private InputStream inputStream;
    private String title;
    private String publisher;
    private Date publishDate;
    private int pageCount;
    private String description;
    private int totalAmount;
    private List<String> authors;
    private List<String> genres;
    private int remainingAmount;
    private String ISBN;
    private int status;

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }


    public String getCover() {
        return cover;
    }


    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
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
        return publishDate;
    }

    public void setPublishDate(Date publish_date) {
        this.publishDate = publish_date;
    }

    public int getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(int remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int page_count) {
        this.pageCount = page_count;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
                ", publish_date=" + publishDate +
                ", page_count=" + pageCount +
                ", description='" + description + '\'' +
                ", totalAmount=" + totalAmount +
                ", ISBN='" + ISBN + '\'' +
                ", status=" + status +
                '}';
    }
}
