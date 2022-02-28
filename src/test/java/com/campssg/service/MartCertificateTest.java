package com.campssg.service;

import com.campssg.dto.mart.MartCertificationRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
public class MartCertificateTest {

    @MockBean
    MartService martService;

    @Test
    public void 마트인증() {
        MartCertificationRequestDto requestDto = new MartCertificationRequestDto();

        requestDto.setBNo("2208162517");
        requestDto.setPNm("한성숙");
        requestDto.setStartDt("19990602");

        martService.certifyMart(requestDto);
    }
}
