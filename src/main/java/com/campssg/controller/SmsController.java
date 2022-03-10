package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.sms.SendSmsRequestDto;
import com.campssg.dto.sms.SmsCertificationRequestDto;
import com.campssg.dto.sms.SmsResponseDto;
import com.campssg.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SmsController {

    private final SmsService smsService;

    @ApiOperation(value = "인증번호 요청 보내기")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "인증번호 전송 완료")
    })
    @PostMapping("register/sms/send")
    public ResponseEntity<SmsResponseDto> sendSms(@RequestBody SendSmsRequestDto sendSmsRequestDto) throws NoSuchAlgorithmException, URISyntaxException,
            UnsupportedEncodingException, InvalidKeyException, JsonProcessingException {
        SmsResponseDto smsResponseDto = smsService.sendSms(sendSmsRequestDto.getPhoneNumber());
        return ResponseEntity.ok().body(smsResponseDto);
    }

    @ApiOperation(value = "인증번호 일치 확인")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "인증번호 확인 결과 전송")
    })
    @PostMapping("register/sms/verify")
    public ResponseEntity<ResponseMessage> veritySms(@RequestBody SmsCertificationRequestDto smsCertificationRequestDto) {
        ResponseMessage responseMessage = smsService.verifySms(smsCertificationRequestDto);
        return ResponseEntity.ok().body(responseMessage);
    }
}
