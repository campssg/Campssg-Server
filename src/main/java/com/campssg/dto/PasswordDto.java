package com.campssg.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "비밀번호 변경")
public class PasswordDto {
    // TODO: 비밀번호 확인 추가
    @NotNull
    private String newPassword;
}
