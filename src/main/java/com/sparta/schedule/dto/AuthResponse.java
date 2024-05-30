package com.sparta.schedule.dto;

import lombok.Getter;

@Getter
public class AuthResponse {
    private String message;
    private String token;

    public AuthResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }
}