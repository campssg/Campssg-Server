package com.campssg.dto.user;

import com.campssg.DB.entity.Role;
import com.campssg.DB.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String userNickname;

    @NotNull
    @Size(min = 2, max = 50)
    private String userName;

    @NotNull
    @Size(min = 5, max = 50)
    private String userEmail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 5, max = 100)
    private String userPassword;

    @NotNull
    @Size(min = 5, max = 50)
    private String phoneNumber;

    private Role userRole;

    public static UserDto from(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .userEmail(user.getUserEmail())
                .userName(user.getUserName())
                .userNickname(user.getUserNickname())
                .userRole(user.getUserRole())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
