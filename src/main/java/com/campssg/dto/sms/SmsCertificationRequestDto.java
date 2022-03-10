package com.campssg.dto.sms;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "인증번호 일치 확인 요청")
public class SmsCertificationRequestDto {
    private String phoneNumber;
    private String certificationNumber;
}
