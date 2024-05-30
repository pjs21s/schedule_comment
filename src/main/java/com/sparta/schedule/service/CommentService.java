package com.sparta.schedule.service;

import com.sparta.schedule.dto.CommentCreateRequest;
import com.sparta.schedule.dto.CommentResponse;
import com.sparta.schedule.dto.CommentUpdateRequest;
import com.sparta.schedule.exception.DataNotFoundException;
import com.sparta.schedule.model.Comment;
import com.sparta.schedule.model.Schedule;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleService scheduleService;
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CommentResponse save(Long scheduleId, CommentCreateRequest request) {

        // 선택한 일정의 아이디를 입력받지 않은 경우
        if (scheduleId == null) {
            throw new IllegalArgumentException("Schedule 아이디는 null이 될 수 없습니다.");
        }

        // DB에 일정이 존재하지 않는 경우
        Schedule schedule = scheduleService.findScheduleById(scheduleId);

        Comment comment = commentRepository.save(new Comment(request.getComment(), request.getUsername(), schedule));
        return CommentResponse.toDto(comment);
    }

    @Transactional
    public CommentResponse update(Long scheduleId, Long commentId, CommentUpdateRequest request) {
        // 선택한 일정이나 댓글 아이디가 입력받지 않은 경우
        if (commentId == null || scheduleId == null) {
            throw new IllegalArgumentException("선택한 일정이나 댓글 ID가 입력되지 않았습니다.");
        }

        // DB에 일정이 존재하지 않는 경우
        scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 맞는 일정 데이터가 없습니다. 아이디 : " + scheduleId));

        // 해당 댓글이 DB에 존재하지 않는 경우
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException("해당 댓글이 DB에 존재하지 않습니다."));

        // 사용자가 일치하지 않는 경우
        if (!Objects.equals(comment.getUsername(), request.getUsername())) {
            throw new IllegalArgumentException("사용자가 일치하지 않습니다.");
        }

        comment.update(request.getComment());
        return CommentResponse.toDto(comment);
    }

    public void delete(Long scheduleId, Long commentId, String username) {
        if (scheduleId == null || commentId == null) {
            throw new IllegalArgumentException("댓글 혹은 일정의 아이디가 없습니다.");
        }

        // DB에 일정이 존재하지 않는 경우
        scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 맞는 일정 데이터가 없습니다. 아이디 : " + scheduleId));

        // 해당 댓글이 DB에 존재하지 않는 경우
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException("해당 댓글이 DB에 존재하지 않습니다."));

        if (!Objects.equals(comment.getUsername(), username)) {
            throw new IllegalArgumentException("사용자가 일치하지 않습니다.");
        }

        commentRepository.delete(comment);
    }
}