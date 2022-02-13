package com.campssg.demo.DB.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(length = 45, nullable = false, unique = true)
    private String user_email;

    @Column(length = 100)
    private String user_password;

    @Column(length = 45, nullable = false)
    private String user_name;

    @Column(length = 45)
    private String user_nickname;

    @Column(nullable = false)
    private String phone_number;

    @Column
    private String social_platform;

    @Column
    private String access_token;

    @Column
    private OffsetDateTime created_at;

    @Column
    private OffsetDateTime updated_at;

    @Builder
    public User(Long user_id, String user_email, String user_password, String user_name, String phone_number) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_name = user_name;
        this.phone_number = phone_number;
    }
}
