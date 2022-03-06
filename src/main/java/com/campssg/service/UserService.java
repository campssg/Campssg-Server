package com.campssg.service;

import com.campssg.DB.entity.Role;
import com.campssg.DB.entity.User;
import com.campssg.DB.repository.UserRepository;
import com.campssg.config.jwt.TokenProvider;
import com.campssg.dto.TokenDto;
import com.campssg.dto.UserDto;
import com.campssg.dto.LoginRequestDto;
import com.campssg.exception.DuplicateMemberException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

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
                .userRole(Role.ROLE_USER)
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
}
