package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ScheduleUpdateRequest {

    /**
     * id.
     */
    private Long id;

    /**
     * title.
     */
    private String title;

    /**
     * 설명.
     */
    private String description;

    @NotNull
    private String password;

    /**
     * 생성자.
     *
     * @param id          id
     * @param title       제목
     * @param description 설명
     */
    public ScheduleUpdateRequest(Long id, String title, String description, String password) {
        this.id = id;
        this.title = title;
        this.password = password;
        this.description = description;
    }
}