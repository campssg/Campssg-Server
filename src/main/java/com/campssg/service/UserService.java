package com.campssg.service;

import com.campssg.DB.entity.Role;
import com.campssg.DB.entity.User;
import com.campssg.DB.repository.UserRepository;
import com.campssg.config.jwt.TokenProvider;
import com.campssg.dto.*;
import com.campssg.exception.DuplicateMemberException;
import com.campssg.util.SecurityUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Transactional
    public UserDto registerUser(UserDto userDto) {
        if (userRepository.findByUserEmail(userDto.getUserEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다");
        }

        User user = User.builder()
                .userEmail(userDto.getUserEmail())
                .userPassword(passwordEncoder.encode(userDto.getUserPassword()))
                .userName(userDto.getUserName())
                .phoneNumber(userDto.getPhoneNumber())
                .userRole(Role.ROLE_GUEST)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional
    public UserDto registerManager(UserDto userDto) {
        if (userRepository.findByUserEmail(userDto.getUserEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다");
        }

        User user = User.builder()
                .userEmail(userDto.getUserEmail())
                .userPassword(passwordEncoder.encode(userDto.getUserPassword()))
                .userName(userDto.getUserName())
                .phoneNumber(userDto.getPhoneNumber())
                .userRole(Role.ROLE_MANAGER)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional
    public TokenDto login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUserEmail(), loginRequestDto.getUserPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        return new TokenDto(jwt);
    }

    @Transactional(readOnly = true)
    public UserDto getMyInfo() {
        return UserDto.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElse(null));
    }

    @Transactional
    public UserDto updateUserNickname(NicknameDto nicknameDto) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElse(null);
        user.setUserNickname(nicknameDto.getUserNickname());
        return UserDto.from(userRepository.save(user));
    }

    // TODO: 비밀번호 변경 추가
    @Transactional
    public TokenDto updateUserPassword(PasswordDto passwordDto) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElse(null);
        user.setUserPassword(passwordDto.getNewPassword());

        User modifyUser = User.builder()
                .userPassword(passwordEncoder.encode(user.getUserPassword()))
                .build();

        userRepository.save(modifyUser);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(modifyUser.getUserEmail(), modifyUser.getUserPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        return new TokenDto(jwt);
    }
}
