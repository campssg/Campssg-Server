package com.campssg.service;

import com.campssg.DB.entity.Certification;
import com.campssg.DB.repository.CertificationRepository;
import com.campssg.dto.*;
import com.campssg.dto.sms.SmsCertificationRequestDto;
import com.campssg.dto.sms.SmsMessageDto;
import com.campssg.dto.sms.SmsRequestDto;
import com.campssg.dto.sms.SmsResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
public class SmsService {

    private final CertificationRepository certificationRepository;

    @Value("${sms.serviceId}")
    private String serviceId;
    @Value("${sms.accessKey}")
    private String accessKey;
    @Value("${sms.secretKey}")
    private String secretKey;
    @Value("${sms.from}")
    private String from;

    public SmsResponseDto sendSms(String phoneNumber) throws JsonProcessingException,
            UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, URISyntaxException {
        Long time = System.currentTimeMillis();

        String number = randomNumber(); // ?????? ????????????(6?????? ??????)
        String content = "??????????????? [" + number + "] ?????????"; // ?????? ?????? ??????
        List<SmsMessageDto> messages = new ArrayList<>();
        messages.add(new SmsMessageDto(phoneNumber, content));

        // json ????????? ??????
        SmsRequestDto smsRequestDto = new SmsRequestDto("SMS", "COMM", "82", this.from, "campssg", messages);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(smsRequestDto);

        // header ??????
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", this.accessKey);
        String sign = makeSignature(time); // ??????
        headers.set("x-ncp-apigw-signature-v2", sign);

        // json body, header ?????????
        HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);

        // API ??? ???????????? ?????? ?????? ?????? (POST)
        RestTemplate restTemplate = new RestTemplate();
        SmsResponseDto smsResponseDto = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+this.serviceId+"/messages"), body, SmsResponseDto.class);

        // ????????? ???????????? ??????, ?????? ?????? ?????? update
        Certification certification = certificationRepository.findByPhoneNumber(phoneNumber).orElseGet(Certification::new);
        certification.setCertificationNumber(number);
        if (certification.getPhoneNumber() == null) {
            certification.setPhoneNumber(phoneNumber);
        }
        certificationRepository.save(certification);

        return smsResponseDto;
    }

    // ????????? ???????????? ?????? ????????????
    public ResponseMessage verifySms(SmsCertificationRequestDto smsCertificationRequestDto) {
        Certification certification = certificationRepository.findByPhoneNumber(smsCertificationRequestDto.getPhoneNumber()).orElseThrow(null);
        LocalDateTime localDateTime = LocalDateTime.now();
        long seconds = ChronoUnit.SECONDS.between(certification.getUpdatedAt(), localDateTime); // ?????? ?????? ??????
        if (seconds > 180) {
            return ResponseMessage.res(HttpStatus.OK, "??????????????? ?????????????????????.");
        } else if (certification.getCertificationNumber().equals(smsCertificationRequestDto.getCertificationNumber())) {
            certificationRepository.delete(certification);
            return ResponseMessage.res(HttpStatus.OK, "????????????");
        } else {
            return ResponseMessage.res(HttpStatus.OK, "??????????????? ????????????.");
        }
    }

    // ?????? ??????
    public String makeSignature(Long time) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/" + this.serviceId + "/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        String endcodeBase64String = Base64.encodeBase64String(rawHmac);

        return endcodeBase64String;
    }

    // ???????????? ??????(6?????? ??????)
    public String randomNumber() {
        Random random = new Random();
        int number = 0; // 1?????? ??????
        String stringNumber = ""; //1?????? ????????? String ?????? ?????????
        String resultNumber = ""; // ??????????????? ?????? 6?????? ?????? string

        for (int i = 0; i < 6; i++) {
            number = random.nextInt(9);
            stringNumber = Integer.toString(number);
            resultNumber += stringNumber;
        }

        return  resultNumber;
    }
}
