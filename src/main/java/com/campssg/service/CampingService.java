package com.campssg.service;

import com.campssg.dto.camping.CampingList;
import com.campssg.dto.camping.CampingRequestDto;
import com.campssg.dto.camping.CampingResponseDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
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
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CampingService {

    @Value("${camping.serviceKey}")
    private String serviceKey;

    // 키워드 기반으로 캠핑장 검색
    public CampingResponseDto<CampingList> searchKeyword(String keyword, String page) throws IOException, ParseException {
        // 공공데이터 api 호출을 위한 url 객체 생성
        URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/searchList?serviceKey="
                +this.serviceKey+"&numOfRows=20&pageNo=" +
                page + "&MobileOS=ETC&MobileApp=campssg&_type=json&keyword="+ URLEncoder.encode(keyword, StandardCharsets.UTF_8));

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
        JSONObject response = (JSONObject) object.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray item = (JSONArray) items.get("item");

        // 전체 결과 수와 페이지 수 추출
        String totalCount = body.get("totalCount").toString();
        String pageNo = body.get("pageNo").toString();

        // JSONArray 하나씩 파싱하면서 원하는 정보만 추출하여 리스트에 저장
        List<CampingList> campingLists = new ArrayList<>();
        for (int i=0;i<item.size();i++) {
            JSONObject obj = (JSONObject) item.get(i);
            CampingList campingList1 = new CampingList(obj);
            campingLists.add(campingList1);
        }

        return new CampingResponseDto<CampingList>(totalCount, pageNo, campingLists);
    }

    // 위치 기반으로 캠핑장 검색
    public CampingResponseDto<CampingList> searchPlace(CampingRequestDto requestDto, String page) throws IOException, ParseException {
        // 공공데이터 api 호출을 위한 url 객체 생성
        URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/locationBasedList?serviceKey=" +
                this.serviceKey +
                "&pageNo=" +
                page +
                "&numOfRows=20&MobileOS=ETC&MobileApp=campssg&mapX=" +
                requestDto.getMapX() +
                "&mapY=" +
                requestDto.getMapY() +
                "&radius=20000&_type=json");

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
        JSONObject response = (JSONObject) object.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray item = (JSONArray) items.get("item");

        // 전체 결과 수와 페이지 수 추출
        String totalCount = body.get("totalCount").toString();
        String pageNo = body.get("pageNo").toString();

        // JSONArray 하나씩 파싱하면서 원하는 정보만 추출하여 리스트에 저장
        List<CampingList> campingLists = new ArrayList<>();
        for (int i=0;i<item.size();i++) {
            JSONObject obj = (JSONObject) item.get(i);
            CampingList campingList1 = new CampingList(obj);
            campingLists.add(campingList1);
        }

        return new CampingResponseDto<CampingList>(totalCount, pageNo, campingLists);
    }
}
