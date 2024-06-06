package com.sparta.schedule.dto;

import lombok.Getter;

@Getter
public class TokenValidationResponse {
    private boolean valid;
    private String username;

    public TokenValidationResponse(boolean valid, String username) {
        this.valid = valid;
        this.username = username;
    }
}
