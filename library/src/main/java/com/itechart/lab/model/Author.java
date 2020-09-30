package com.itechart.lab.model;

import java.util.Objects;

public class Author implements Entity {
    private int id;
    private String name;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstname) {
        this.name = firstname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return getId() == author.getId() &&
                getName().equals(author.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstname='" + name + '\'' +
                ", lastname='" +
                '}';
    }
}
