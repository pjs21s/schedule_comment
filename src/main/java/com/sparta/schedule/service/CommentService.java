package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentCreateRequest;
import com.sparta.schedule.dto.CommentResponse;
import com.sparta.schedule.dto.CommentUpdateRequest;
import com.sparta.schedule.exception.DataNotFoundException;
import com.sparta.schedule.model.Comment;
import com.sparta.schedule.model.Schedule;
import com.sparta.schedule.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleService scheduleService;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse save(long scheduleId, CommentCreateRequest request) {

        // DB에 일정이 존재하지 않는 경우
        Schedule schedule = scheduleService.findScheduleById(scheduleId);
        Comment comment = commentRepository.save(new Comment(request.getComment(), request.getUsername(), schedule));
        return CommentResponse.toDto(commentRepository.save(comment));
    }

    @Transactional
    public CommentResponse update(long scheduleId, long commentId, CommentUpdateRequest request) {

        // DB에 일정이 존재하지 않는 경우
        scheduleService.findScheduleById(scheduleId);
        // 해당 댓글이 DB에 존재하지 않는 경우
        Comment comment = findById(commentId);

        // 사용자가 일치하지 않는 경우
        if (!Objects.equals(comment.getUsername(), request.getUsername())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        comment.update(request.getComment());
        return CommentResponse.toDto(comment);
    }

    @Transactional
    public void delete(long scheduleId, long commentId, String username) {

        // DB에 일정이 존재하지 않는 경우
        scheduleService.findScheduleById(scheduleId);
        // 해당 댓글이 DB에 존재하지 않는 경우
        Comment comment = findById(commentId);
        // 작성자가 동일하지 않는 경우
        if (!Objects.equals(comment.getUsername(), username)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException("해당 댓글이 DB에 존재하지 않습니다."));
    }
}