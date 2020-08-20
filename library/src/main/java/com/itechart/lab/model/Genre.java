package com.itechart.lab.model;

import java.util.Objects;

public class Genre {
    private int id;
    private String genre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre1 = (Genre) o;
        return getId() == genre1.getId() &&
                getGenre().equals(genre1.getGenre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGenre());
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                '}';
    }
}
