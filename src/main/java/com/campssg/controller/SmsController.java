package com.campssg.controller;

import com.campssg.dto.SendSmsRequestDto;
import com.campssg.dto.SmsResponseDto;
import com.campssg.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @PostMapping("register/sms")
    public ResponseEntity<SmsResponseDto> sms(@RequestBody SendSmsRequestDto sendSmsRequestDto) throws NoSuchAlgorithmException, URISyntaxException,
            UnsupportedEncodingException, InvalidKeyException, JsonProcessingException {
        SmsResponseDto smsResponseDto = smsService.sendSms(sendSmsRequestDto.getPhoneNumber());
        return ResponseEntity.ok().body(smsResponseDto);
    }
}
