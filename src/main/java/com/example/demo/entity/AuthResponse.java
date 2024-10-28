package com.example.demo.entity;





public class AuthResponse {
    private String jwt;
    private User user;

    public AuthResponse(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }

    // Getters and Setters
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
