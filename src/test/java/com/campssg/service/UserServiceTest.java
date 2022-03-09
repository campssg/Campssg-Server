package com.campssg.service;

import com.campssg.DB.entity.User;
import com.campssg.DB.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void 비밀번호_일치_확인() {
        User user = userRepository.findByUserEmail("campssg@example.com").orElseThrow();
        if (passwordEncoder.matches("userPassword", user.getUserPassword())) {
            System.out.println("일치");
        } else {
            System.out.println("불일치");
        }

    }
}
