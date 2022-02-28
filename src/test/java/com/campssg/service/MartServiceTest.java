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

    @Test
    public void saveMart() {
        //     public Mart(Long martId, User user, String martName, String martAddress, Long requestYn, String openTime, String closeTime) {
        User user = userRepository.findByUserEmail("test5@example.com");
        Mart entity = new Mart(null, user, "TEST MART", "인천광역시 연수구 아카데미로 119", (long) 1, "10:00", "20:00");
        martRepository.save(entity);
    }
}
