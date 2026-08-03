package com.example.auth_service.dto;

public class AuthResponse {

    private final String token;
    private final Long userId;
    private final String name;
    private final String role;

    public AuthResponse(String token, Long userId, String name, String role) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
