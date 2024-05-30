package com.sparta.schedule.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 8, max = 15)
    @Column(nullable = false)
    private String password;
    private String authority;

    public void setUserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
