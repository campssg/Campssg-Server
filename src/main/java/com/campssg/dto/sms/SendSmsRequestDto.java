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
@ApiModel(value = "클라이언트에서 인증번호 전송 요청")
public class SendSmsRequestDto {
    private String phoneNumber;
}
