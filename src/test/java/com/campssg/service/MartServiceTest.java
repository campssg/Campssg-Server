package com.campssg.service;

import com.campssg.DB.entity.Mart;
import com.campssg.DB.entity.User;
import com.campssg.DB.repository.MartRepository;
import com.campssg.DB.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MartServiceTest {

    @Autowired
    MartRepository martRepository;

    @Autowired
    UserRepository userRepository;

}
