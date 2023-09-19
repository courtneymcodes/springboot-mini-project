package com.example.springbootminiproject.model;

public class User {
    private Long id;
    private String emailAddress;
    private String password;

    public User() {
    }

    public User(Long id, String emailAddress, String password) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
