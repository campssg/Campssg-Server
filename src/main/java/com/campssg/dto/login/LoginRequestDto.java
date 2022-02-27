package com.campssg.dto.login;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "로그인 요청")
public class LoginRequestDto {
    @NotNull(message = "email는 필수입니다.")
    private String userEmail;

    @NotNull(message = "password는 필수입니다.")
    private String userPassword;
}
