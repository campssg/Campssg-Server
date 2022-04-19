package com.campssg.service;

import com.campssg.DB.entity.Role;
import com.campssg.DB.entity.User;
import com.campssg.DB.repository.UserRepository;
import com.campssg.common.S3Uploder;
import com.campssg.config.jwt.TokenProvider;
import com.campssg.dto.user.DeleteRequestDto;
import com.campssg.dto.user.LoginRequestDto;
import com.campssg.dto.user.NicknameDto;
import com.campssg.dto.user.PasswordDto;
import com.campssg.dto.user.TokenDto;
import com.campssg.dto.user.UserDto;
import com.campssg.exception.DuplicateMemberException;
import com.campssg.util.SecurityUtil;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final S3Uploder s3Uploder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
        TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, S3Uploder s3Uploder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.s3Uploder = s3Uploder;
    }

    @Transactional
    public UserDto registerUser(UserDto userDto, MultipartFile file) throws IOException {
        if (userRepository.findByUserEmail(userDto.getUserEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다");
        }

        String imgUrl = file == null ? null : s3Uploder.upload(file, "user");

        User user = User.builder()
            .userEmail(userDto.getUserEmail())
            .userPassword(passwordEncoder.encode(userDto.getUserPassword()))
            .userName(userDto.getUserName())
            .userImg(imgUrl)
            .phoneNumber(userDto.getPhoneNumber())
            .userRole(Role.ROLE_GUEST)
            .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional
    public UserDto registerManager(UserDto userDto, MultipartFile file) throws IOException {
        if (userRepository.findByUserEmail(userDto.getUserEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다");
        }

        String imgUrl = file == null ? null : s3Uploder.upload(file, "user");

        User user = User.builder()
            .userEmail(userDto.getUserEmail())
            .userPassword(passwordEncoder.encode(userDto.getUserPassword()))
            .userName(userDto.getUserName())
            .userImg(imgUrl)
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

    // 비밀번호 일치 확인 후 일치하면 비밀번호 변경
    @Transactional
    public UserDto updateUserPassword(PasswordDto passwordDto) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElse(null);
        if (passwordEncoder.matches(passwordDto.getRecentPassword(), user.getUserPassword())) {
            user.setUserPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        } else {
            logger.info("비밀번호가 일치하지 않습니다");
        }
        return UserDto.from(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(DeleteRequestDto deleteRequestDto) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElse(null);
        if (passwordEncoder.matches(deleteRequestDto.getUserPassword(), user.getUserPassword())) {
            userRepository.delete(user);
        } else {
            logger.info("비밀번호가 일치하지 않습니다");
        }
    }
}
