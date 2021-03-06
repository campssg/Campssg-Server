package com.campssg.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "닉네임 추가 또는 수정")
public class NicknameDto {

    @NotNull
    private String userNickname;
}
