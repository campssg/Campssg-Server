package com.campssg.controller;

import com.campssg.config.jwt.JwtFilter;
import com.campssg.dto.ResponseMessage;
import com.campssg.dto.TokenDto;
import com.campssg.dto.UserDto;
import com.campssg.dto.LoginRequestDto;
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

    @ApiOperation(value = "서비스 이용자 권한 및 정보 등록")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "서비스 이용자 권한 및 정보 등록 완료")
    })
    @PostMapping("/register/user")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @ApiOperation(value = "서비스 이용자 권한 및 정보 등록")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "서비스 이용자 권한 및 정보 등록 완료")
    })
    @PostMapping("/register/manager")
    public ResponseEntity<UserDto> registerManager(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.registerManager(userDto));
    }

    @ApiOperation(value = "사용자 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "사용자 정보 조회 완료")
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseMessage<TokenDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        TokenDto tokenDto = userService.login(loginRequestDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenDto.getToken());

        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "조회 성공", tokenDto),
                httpHeaders, HttpStatus.OK);
    }
}
