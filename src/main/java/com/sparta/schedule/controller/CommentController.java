package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommentCreateRequest;
import com.sparta.schedule.dto.CommentResponse;
import com.sparta.schedule.dto.CommentUpdateRequest;
import com.sparta.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule/{scheduleId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @PostMapping
    public ResponseEntity<CommentResponse> create(
            @PathVariable(name = "scheduleId") long scheduleId,
            @RequestBody CommentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(scheduleId, request));
    }

    @PatchMapping("{commentId}")
    public ResponseEntity<CommentResponse> update(
            @PathVariable(name = "scheduleId") long scheduleId,
            @PathVariable(name = "commentId") long commentId,
            @RequestBody CommentUpdateRequest request) {

        return ResponseEntity.ok().body(service.update(scheduleId, commentId, request));
    }
}