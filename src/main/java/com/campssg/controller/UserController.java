package com.campssg.controller;

import com.campssg.config.jwt.JwtFilter;
import com.campssg.dto.*;
import com.campssg.dto.user.*;
import com.campssg.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "서비스 이용자 권한 및 정보 등록")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "서비스 이용자 권한 및 정보 등록 완료")
    })
    @PostMapping("/register/guest")
    public ResponseEntity<UserDto> registerUser(@Valid @ModelAttribute UserDto userDto,
        @RequestPart(value = "img", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(userService.registerUser(userDto, file));
    }

    @ApiOperation(value = "마트 운영자 권한 및 정보 등록")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "마트 운영자 권한 및 정보 등록 완료")
    })
    @PostMapping("/register/manager")
    public ResponseEntity<UserDto> registerManager(@Valid @ModelAttribute UserDto userDto,
        @RequestPart(value = "img", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(userService.registerManager(userDto, file));
    }

    @ApiOperation(value = "사용자 로그인")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "사용자 로그인 완료")
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseMessage<TokenDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        TokenDto tokenDto = userService.login(loginRequestDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenDto.getToken());

        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "조회 성공", tokenDto),
            httpHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 정보 조회")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "사용자 정보 조회 완료")
    })
    @GetMapping("/info")
    @PreAuthorize("hasAnyRole('GUEST', 'MANAGER')")
    public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyInfo());
    }

    @ApiOperation(value = "닉네임 추가 또는 수정")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "닉네임 추가 또는 수정 완료")
    })
    @PatchMapping("/update/nickname")
    @PreAuthorize("hasAnyRole('GUEST', 'MANAGER')")
    public ResponseEntity<UserDto> updateUserNickname(HttpServletRequest request, @Valid @RequestBody NicknameDto nicknameDto) {
        return ResponseEntity.ok(userService.updateUserNickname(nicknameDto));
    }

    @ApiOperation(value = "비밀번호 변경")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "비밀번호 변경 완료")
    })
    @PatchMapping("/update/password")
    @PreAuthorize("hasAnyRole('GUEST', 'MANAGER')")
    public ResponseEntity<UserDto> updateUserPassword(HttpServletRequest request, @Valid @RequestBody PasswordDto passwordDto) {
        return ResponseEntity.ok(userService.updateUserPassword(passwordDto));
    }

    @ApiOperation(value = "회원 이미지 변경")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "회원 이미지 변경 완료")
    })
    @PatchMapping("/update/img")
    @PreAuthorize("hasAnyRole('GUEST', 'MANAGER')")
    public ResponseEntity<ResponseMessage> updateUserPassword(@ModelAttribute MultipartFile file)
        throws IOException {
        userService.updateUserImg(file);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "이미지 변경이 완료되었습니다"));
    }

    @ApiOperation(value = "회원 탈퇴")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "회원 탈퇴 완료")
    })
    @PostMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteUser(@RequestBody DeleteRequestDto deleteRequestDto) {
        userService.deleteUser(deleteRequestDto);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "탈퇴가 완료되었습니다"));
    }

    @ApiOperation(value = "사용자가 조회하는 마트정보")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "회원 탈퇴 완료")
    })
    @GetMapping("/mart/{martId}")
    public ResponseEntity<UserMartResponseDto> deleteUser(@PathVariable Long martId) {
        return ResponseEntity.ok(userService.getMartInfo(martId));
    }
}
