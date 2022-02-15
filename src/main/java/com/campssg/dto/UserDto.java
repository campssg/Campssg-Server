package com.campssg.dto;

import com.campssg.DB.entity.User;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
    private Long user_id;
    private String user_email;
    private String user_password;
    private String user_name;
    private String phone_number;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;

    public User toEntity() {
        return User.builder()
                .user_id(user_id)
                .user_email(user_email)
                .user_password(user_password)
                .user_name(user_name)
                .phone_number(phone_number)
                .build();
    }

    @Builder
    public UserDto(Long user_id, String user_email, String user_password, String user_name, String phone_number) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_name = user_name;
        this.phone_number = phone_number;
    }
}
