package com.campssg.dto.sms;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ApiModel(value = "인증번호 전송 문자")
public class SmsMessageDto {
    private String to;
    private String content;
}
