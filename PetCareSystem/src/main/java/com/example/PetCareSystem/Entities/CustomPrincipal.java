package com.example.PetCareSystem.Entities;

public class CustomPrincipal {
    private final Integer userId;
    private final String username;

    public CustomPrincipal(Integer userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
