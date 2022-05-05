package com.campssg.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMartResponseDto {
    private String martName;
    private String martNumber;
    private String martAddress;
}
