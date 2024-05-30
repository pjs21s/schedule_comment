package com.sparta.schedule.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    @Email
    private String username;

    @Column(length = 100, nullable = false)
    @NotBlank
    private String password;

    @Column
    @Length(max = 200)
    @NotBlank
    private String title;

    @Column
    private String description;

    // 외래키로 인해서 일정 삭제 시 관련 exception이 발생하므로 orphanRemoval을 사용
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Schedule(String title, String description, String username, String password) {
        this.title = title;
        this.description = description;
        this.username = username;
        this.password = password;
    }

    public void update(String title, String description) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }

        if (description != null && !description.isBlank()) {
            this.description = description;
        }
    }
}