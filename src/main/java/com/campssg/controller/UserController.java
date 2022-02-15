package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.login.LoginRequestDto;
import com.campssg.dto.login.LoginResponseDto;
import com.campssg.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @ApiOperation(value = "사용자 정보 조회")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "사용자 정보 조회 완료")
    })
    @PostMapping
    public ResponseEntity<ResponseMessage<LoginResponseDto>> defaultLogin(@RequestBody @Validated LoginRequestDto requestDto) {
        LoginResponseDto responseDto = userService.defaultLogin(requestDto);
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "조회 성공", responseDto), HttpStatus.OK);
    }
}
