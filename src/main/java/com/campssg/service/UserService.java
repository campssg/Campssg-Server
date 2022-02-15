package com.campssg.service;

import com.campssg.DB.entity.User;
import com.campssg.DB.repository.UserRepository;
import com.campssg.config.jwt.JwtTokenProviderFilter;
import com.campssg.dto.UserDto;
import com.campssg.dto.login.LoginRequestDto;
import com.campssg.dto.login.LoginResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long joinUser(UserDto userDto) {
        userDto.setUser_password(passwordEncoder.encode(userDto.getUser_password()));
        return userRepository.save(userDto.toEntity()).getUserId();
    }

    @Transactional
    public LoginResponseDto defaultLogin(LoginRequestDto requestDto) {
        User user = userRepository.findByUserEmail(requestDto.getUserEmail());

        if (passwordEncoder.matches(user.getUserPassword(), requestDto.getPassword())) {
            // JWT token 생성
            return new LoginResponseDto(user, null);
        }
        return null;
    }
}
