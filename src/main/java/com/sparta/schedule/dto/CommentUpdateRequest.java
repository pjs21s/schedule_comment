package com.sparta.schedule.dto;

import lombok.Getter;

@Getter
public class CommentUpdateRequest {

    private String username;
    private String comment;

    public CommentUpdateRequest(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }
}
