package com.itechart.lab.model;

import java.sql.Date;
import java.util.Objects;

public class Reader extends Entity {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private boolean gender;
    private String phone;
    private Date dateOfRegistration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reader)) return false;
        Reader reader = (Reader) o;
        return getId() == reader.getId() &&
                getGender() == reader.getGender() &&
                getFirstname().equals(reader.getFirstname()) &&
                getLastname().equals(reader.getLastname()) &&
                getEmail().equals(reader.getEmail()) &&
                Objects.equals(getPhone(), reader.getPhone()) &&
                Objects.equals(getDateOfRegistration(), reader.getDateOfRegistration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstname(), getLastname(), getEmail(), getGender(), getPhone(), getDateOfRegistration());
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", dateOfRegistration=" + dateOfRegistration +
                '}';
    }
}
