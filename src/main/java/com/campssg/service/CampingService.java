package com.campssg.service;

import com.campssg.dto.camping.CampingRequestDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

@Service
@Transactional
@RequiredArgsConstructor
public class CampingService {

    @Value("${camping.serviceKey}")
    private String serviceKey;

    // 키워드 기반으로 캠핑장 검색
    public JSONObject searchKeyword(String keyword) throws IOException, ParseException {
        // 공공데이터 api 호출을 위한 url 객체 생성
        URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/searchList?serviceKey="
                +this.serviceKey+"&numOfRows=50&pageNo=1&MobileOS=ETC&MobileApp=campssg&_type=json&keyword="+ URLEncoder.encode(keyword, StandardCharsets.UTF_8));

        // 공공데이터 api 호출을 위한 Connection 객체 생성
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // 통신을 위한 메소드 설정 (GET)
        conn.setRequestProperty("Content-Type", "application/json"); // 통신을 위한 Content-Type 설정

        System.out.println(conn.getResponseCode()); // 통신 응답 코드 확인

        // 전달받은 데이터를 BufferReader 객체로 저장
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        // 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close(); // 객체 해제
        conn.disconnect(); // 연결 해제

        // 문자열 형태로 저장된 JSON을 파싱하기 위한 JSONParser 객체 생성
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(sb.toString());
        // JSONObject response = (JSONObject) object.get("response");
        // JSONObject body = (JSONObject) response.get("body");
        // JSONObject items = (JSONObject) body.get("items");
        // JSONArray item = (JSONArray) items.get("item");

        return object;
    }

    // 위치 기반으로 캠핑장 검색
    public JSONObject searchPlace(CampingRequestDto requestDto) throws IOException, ParseException {
        // 공공데이터 api 호출을 위한 url 객체 생성
        URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/locationBasedList?serviceKey=" +
                this.serviceKey +
                "&pageNo=1&numOfRows=50&MobileOS=ETC&MobileApp=campssg&mapX=" +
                requestDto.getMapX() +
                "&mapY=" +
                requestDto.getMapY() +
                "&radius=20000&_type=json");

        System.out.println(url);

        // 공공데이터 api 호출을 위한 Connection 객체 생성
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // 통신을 위한 메소드 설정 (GET)
        conn.setRequestProperty("Content-Type", "application/json"); // 통신을 위한 Content-Type 설정

        // 전달받은 데이터를 BufferReader 객체로 저장
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        // 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close(); // 객체 해제
        conn.disconnect(); // 연결 해제

        // 문자열 형태로 저장된 JSON을 파싱하기 위한 JSONParser 객체 생성
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(sb.toString());

        return object;
    }
}
