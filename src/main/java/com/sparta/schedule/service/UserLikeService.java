package com.sparta.schedule.service;

import com.sparta.schedule.model.UserLike;
import com.sparta.schedule.model.User;
import com.sparta.schedule.repository.UserLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserLikeService {

    private final UserLikeRepository likeRepository;
    private final ScheduleService scheduleService;
    private final CommentService commentService;
    private final UserService userService;

    @Transactional
    public UserLike saveLike(String username, Long contentId, String contentType) {
        User user = userService.findByUsername(username);
        UserLike like = new UserLike();
        like.setUser(user);
        like.setContentId(contentId);
        like.setContentType(contentType);
        System.out.println("contentTYpe: " + contentType);
        if ("SCHEDULE".equals(contentType)) {
            scheduleService.findScheduleById(contentId);
        } else if ("COMMENT".equals(contentType)) {
            commentService.findById(contentId);
        } else {
            throw new IllegalArgumentException("Invalid content type");
        }

        return likeRepository.save(like);
    }

}
