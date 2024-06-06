package com.sparta.schedule.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @Length(min = 4, max = 10)
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    public User() {

    }

    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
