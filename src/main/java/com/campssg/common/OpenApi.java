package com.campssg.common;

import com.campssg.dto.mart.MartCertificationRequestDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenApi {

    @Value("${openApi.serviceKey}")
    private String serviceKey;

    /**
     * 사업자등록번호(필수)	숫자로 이루어진 10자리 값만 가능 ('-' 등의 기호 반드시 제거 후 호출) 대표자성명(필수)	외국인 사업자의 경우에는 영문명 입력 개업일자(필수) YYYYMMDD
     */
    public boolean martValidationOpenApi(MartCertificationRequestDto requestDto) {
        boolean isValidate = false;
        try {
            URL url = new URL("https://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=" + URLEncoder
                .encode(serviceKey, "utf-8"));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String jsonInputString =
                "{"
                    + "\"businesses\": ["
                    + "{"
                    + "\"b_no\": \"" + requestDto.getBNo() + "\"" + ","
                    + "\"start_dt\": \"" + requestDto.getStartDt() + "\"" + ","
                    + "\"p_nm\": \"" + requestDto.getPNm() + "\""
                    + "}"
                    + "]"
                    + "}";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input);
            }

            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            ;
            StringBuffer sb = new StringBuffer();
            String responseData = "";

            while ((responseData = br.readLine()) != null) {
                sb.append(responseData); //StringBuffer에 응답받은 데이터 순차적으로 저장 실시
            }
            //메소드 호출 완료 시 반환하는 변수에 버퍼 데이터 삽입 실시
            String returnData = sb.toString();

            //http 요청 응답 코드 확인 실시
            String responseCode = String.valueOf(conn.getResponseCode());
            if (responseCode.equals("200")) {
                isValidate = true;
            }
            System.out.println("http 응답 코드 : " + responseCode);
            System.out.println("http 응답 데이터 : " + returnData);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isValidate;
    }
}
