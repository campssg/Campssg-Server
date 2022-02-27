package com.campssg.controller;

import com.campssg.config.jwt.JwtFilter;
import com.campssg.dto.ResponseMessage;
import com.campssg.dto.UserDto;
import com.campssg.dto.login.LoginRequestDto;
import com.campssg.dto.login.LoginResponseDto;
import com.campssg.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "사용자 정보 등록")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "사용자 정보 등록 완료")
    })
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.register(userDto));
    }

    @ApiOperation(value = "사용자 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "사용자 정보 조회 완료")
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseMessage<LoginResponseDto>> authorize(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + loginResponseDto.getJwt());

        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "조회 성공", loginResponseDto),
                httpHeaders, HttpStatus.OK);
    }
}
