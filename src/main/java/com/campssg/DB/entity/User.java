package com.campssg.DB.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends Auditor {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_email", length = 45, nullable = false, unique = true)
    private String userEmail;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_name", length = 45, nullable = false)
    private String userName;

    @Column(name = "user_img", length = 256, nullable = false)
    private String userImg;

    @Column(name = "user_nickname", length = 45)
    private String userNickname;

    @Column(name = "phone_number", length = 45, nullable = false, unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", length = 45, nullable = false)
    private Role userRole;

    @Column(name = "social_platform", length = 45)
    private String socialPlatform;

    @Column(name = "access_token")
    private String accessToken;

    public User(Long userId) {
        this.userId = userId;
    }

    public void updateImg(String userImg) {
        this.userImg = userImg;
    }
}
