package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.login.LoginResponseDto;
import com.campssg.dto.mart.MartCertificationRequestDto;
import com.campssg.dto.mart.MartListResponseDto;
import com.campssg.dto.mart.MartResponseDto;
import com.campssg.dto.mart.MartSaveRequestDto;
import com.campssg.service.MartService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mart")
@RequiredArgsConstructor
public class MartController {

    @Autowired
    private final MartService martService;

    @ApiOperation(value = "마트 인증")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "마트 인증 완료")
    })
    @PostMapping("/certificate")
    public ResponseEntity certifyMart(
        @RequestBody @Validated MartCertificationRequestDto requestDto) {
        martService.certifyMart(requestDto);
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "마트 등록 성공", null), HttpStatus.OK);
    }

    @ApiOperation(value = "마트 등록")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "마트 등록 완료")
    })
    @PostMapping
    public ResponseEntity<ResponseMessage<LoginResponseDto>> defaultLogin(
        @RequestBody @Validated MartSaveRequestDto requestDto) {
        martService.saveMart(requestDto);
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "마트 등록 성공", null), HttpStatus.OK);
    }

    @ApiOperation(value = "마트 조회")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "마트 조회 완료")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseMessage<List<MartListResponseDto>>> martList(@PathVariable Long userId) {
        List<MartListResponseDto> response = martService.findByUserId(userId);
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "마트 조회 성공", response), HttpStatus.OK);
    }
}
