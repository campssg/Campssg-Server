package com.campssg.dto.sms;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ApiModel(value = "인증번호 문자 전송 완료")
public class SmsResponseDto {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;
}
