package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleCreateRequest;
import com.sparta.schedule.dto.ScheduleDeleteRequest;
import com.sparta.schedule.dto.ScheduleResponse;
import com.sparta.schedule.dto.ScheduleUpdateRequest;
import com.sparta.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> findById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok()
                .body(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> findAll() {
        return ResponseEntity.ok()
                .body(service.findAll());
    }

    @PostMapping
    public ResponseEntity<ScheduleResponse> create(
            @Valid @RequestBody ScheduleCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @PutMapping
    public ResponseEntity<ScheduleResponse> update(
            @Valid @RequestBody ScheduleUpdateRequest request) {
        return ResponseEntity.ok().body(service.update(request));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@Valid @RequestBody ScheduleDeleteRequest request) {
        service.delete(request);
        return ResponseEntity.ok()
                .body("성공적으로 삭제되었습니다.");
    }
}