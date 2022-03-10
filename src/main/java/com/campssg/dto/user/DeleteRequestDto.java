package com.campssg.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "회원 탈퇴")
public class DeleteRequestDto {
    private String userPassword;
}
