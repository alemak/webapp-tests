package com.netaporter.pws.automation.shared.pojos;

public class Customer {

    private String fname;
    private String surname;
    private String email;
    private String password;
    private String country;
    private Integer id;

    public Customer(String fname, String surname, String email, String password, String country) {
        this.fname = fname;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.country = country;
    }

    public Customer() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
