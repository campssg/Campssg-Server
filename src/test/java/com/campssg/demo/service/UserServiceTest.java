package com.campssg.demo.service;

import com.campssg.demo.DB.entity.User;
import com.campssg.demo.DB.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        User user = new User(1L,"1", "1", "1", "1");
    }
}
