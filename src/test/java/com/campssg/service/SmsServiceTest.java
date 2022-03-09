package com.campssg.service;

import com.campssg.DB.entity.Certification;
import com.campssg.DB.repository.CertificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class SmsServiceTest {
    @Autowired
    private SmsService smsService;

    @Autowired
    private CertificationRepository certificationRepository;

    @Test
    public void 생성한_난수_저장() {
        String number = smsService.randomNumber();
        Certification certification = certificationRepository.findByPhoneNumber("휴대폰번호").orElseGet(Certification::new);
        certification.setCertificationNumber(number);
        if (certification.getPhoneNumber() == null) {
            certification.setPhoneNumber("휴대폰번호");
        }
        certificationRepository.save(certification);
    }

    @Test
    public void 인증번호_일치_확인() {
        Certification certification = certificationRepository.findByPhoneNumber("휴대폰번호").orElseThrow();

        LocalDateTime localDateTime = LocalDateTime.now();
        long seconds = ChronoUnit.SECONDS.between(certification.getUpdatedAt(), localDateTime);
        System.out.println(seconds);

        if (seconds > 180) {
            System.out.println("시간이 만료되었습니다. 인증번호를 다시 발급받으세요.");
        } else if (certification.getCertificationNumber().equals("인증번호")) {
            System.out.println("일치합니다");
            certificationRepository.delete(certification);
        } else {
            System.out.println("인증번호가 틀립니다");
        }
    }
}
