package com.sparta.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.schedule.model.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private Long id;
    private String comment;
    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private Long scheduleId;

    public CommentResponse(Long id, String comment, String username, Long scheduleId, LocalDateTime createdAt) {
        this.id = id;
        this.comment = comment;
        this.username = username;
        this.scheduleId = scheduleId;
        this.createdAt = createdAt;
    }

    public static CommentResponse toDto(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUsername(),
                comment.getSchedule().getId(),
                comment.getCreatedAt()
        );
    }
}

