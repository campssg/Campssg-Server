package com.campssg.DB.entity;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_email", length = 45, nullable = false, unique = true)
    private String userEmail;

    @Column(name = "user_password", length = 100)
    private String userPassword;

    @Column(name = "user_name", length = 45, nullable = false)
    private String userName;

    @Column(name = "user_nickname", length = 45)
    private String userNickname;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "social_platform")
    private String socialPlatform;

    @Column(name = "access_token")
    private String accessToken;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Builder
    public User(Long user_id, String user_email, String user_password, String user_name, String phone_number) {
        this.userId = user_id;
        this.userEmail = user_email;
        this.userPassword = user_password;
        this.userName = user_name;
        this.phoneNumber = phone_number;
    }

    public List<String> getRoleList() {
        return Arrays.asList(this.roleType.toString());
    }
}
