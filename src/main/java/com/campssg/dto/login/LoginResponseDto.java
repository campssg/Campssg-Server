package com.campssg.dto.login;

import com.campssg.DB.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoginResponseDto {
    private final Long userId;
    private final String userEmail;
    private final String userName;
    private final String phoneNumber;
    private final String jwt;
    private final LocalDateTime createdAt;

    public LoginResponseDto(User entity, String jwt) {
        this.userId = entity.getUserId();
        this.userEmail = entity.getUserEmail();
        this.userName = entity.getUserName();
        this.phoneNumber = entity.getPhoneNumber();
        this.createdAt = entity.getCreatedAt();
        this.jwt = jwt;
    }
}
