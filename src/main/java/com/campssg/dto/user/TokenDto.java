package com.campssg.dto.user;

import com.campssg.DB.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String token;
    private String userName;
    private String userEmail;
    private String userImgUrl;
    private String userRole;
    private String userNickname;

    public TokenDto(String jwt, User user) {
        this.token = jwt;
        this.userEmail = user.getUserEmail();
        this.userName = user.getUserName();
        this.userImgUrl = user.getUserImg();
        this.userRole = user.getUserRole().toString();
        this.userNickname = user.getUserNickname();
    }
}
