package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ScheduleDeleteRequest {

    @NotNull
    private Long id;

    @NotNull
    private String password;

    public ScheduleDeleteRequest(Long id, String password) {
        this.id = id;
        this.password = password;
    }
}