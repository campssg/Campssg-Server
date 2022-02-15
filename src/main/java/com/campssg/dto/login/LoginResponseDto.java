package com.campssg.dto.login;

import com.campssg.DB.entity.User;
import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private Long userId;
    private String userEmail;
    private String userName;
    private String phoneNumber;
    private String jwt;
    private OffsetDateTime createdAt;

    public LoginResponseDto(User entity, String jwt) {
        this.userId = entity.getUserId();
        this.userEmail = entity.getUserEmail();
        this.userName = entity.getUserName();
        this.phoneNumber = entity.getPhoneNumber();
        this.createdAt = entity.getCreatedAt();
        this.jwt = jwt;
    }
}
