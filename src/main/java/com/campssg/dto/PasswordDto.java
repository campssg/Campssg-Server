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
    @NotNull
    private String newPassword;
    // TODO: 비밀번호 변경 추가
}
