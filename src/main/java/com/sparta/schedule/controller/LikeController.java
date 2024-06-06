package com.sparta.schedule.controller;

import com.sparta.schedule.model.UserLike;
import com.sparta.schedule.service.UserLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    private final UserLikeService userLikeService;

    public LikeController(UserLikeService userLikeService) {
        this.userLikeService = userLikeService;
    }

    @PostMapping("/api/likes")
    public ResponseEntity<UserLike> createLike(@RequestBody UserLike like) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserLike savedlike = userLikeService.saveLike(username, like.getContentId(), like.getContentType());

        return ResponseEntity.ok(savedlike);
    }
}
