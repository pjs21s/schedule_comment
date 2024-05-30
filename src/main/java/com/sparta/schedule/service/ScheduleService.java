package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleCreateRequest;
import com.sparta.schedule.dto.ScheduleDeleteRequest;
import com.sparta.schedule.dto.ScheduleResponse;
import com.sparta.schedule.dto.ScheduleUpdateRequest;
import com.sparta.schedule.exception.DataNotFoundException;
import com.sparta.schedule.model.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository repository;

    public ScheduleResponse findById(long id) {
        return ScheduleResponse.toDto(findScheduleById(id));
    }

    protected Schedule findScheduleById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("해당 Id에 맞는 일정을 찾을 수 없습니다."));
    }

    @Transactional
    public ScheduleResponse save(ScheduleCreateRequest request) {
        Schedule schedule = repository.save(request.toEntity());
        return ScheduleResponse.toDto(schedule);
    }

    public List<ScheduleResponse> findAll() {
        List<Schedule> list = repository.findAll();
        return list
                .stream()
                .sorted(Comparator.comparing(Schedule::getCreatedAt).reversed())
                .map(ScheduleResponse::toDto)
                .toList();
    }

    @Transactional
    public ScheduleResponse update(long scheduleId, ScheduleUpdateRequest request) {
        Schedule schedule = findScheduleById(scheduleId);

        if (!schedule.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }

        schedule.update(request.getTitle(), request.getDescription());
        return ScheduleResponse.toDto(schedule);
    }

    @Transactional
    public void delete(long scheduleId, ScheduleDeleteRequest request) {
        Schedule schedule = findScheduleById(scheduleId);

        if (!Objects.equals(schedule.getPassword(), request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }

        if (!Objects.equals(schedule.getUsername(), request.getUsername())) {
            throw new IllegalArgumentException("사용자 이름이 동일하지 않습니다.");
        }

        repository.delete(schedule);
    }
}