package com.campssg;

import com.campssg.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

@SpringBootTest
public class CampssgApplicationTest {
    @Autowired
    private SmsService smsService;

    @Test
    void sendSms() throws JsonProcessingException, ParseException, UnsupportedEncodingException, URISyntaxException,
            NoSuchAlgorithmException, InvalidKeyException {
        smsService.sendSms("수신번호", "test");
    }

    @Test
    void contextLoads() {
    }
}
