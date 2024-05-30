package com.sparta.schedule.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "공백이거나 null인 것은 불가합니다.")
    private String comment;

    @Column(length = 100)
    @Email
    private String username;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

}