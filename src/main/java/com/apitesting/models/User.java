package com.apitesting.models;

public class User {
    private String username;
    private String email;
    private Integer id;

    // Constructor with parameters
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Empty constructor (needed for JSON deserialization)
    public User() {
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}