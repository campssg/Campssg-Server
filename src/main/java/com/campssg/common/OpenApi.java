package com.campssg.common;

import com.campssg.dto.mart.MartCertificationRequestDto;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenApi {

    @Value("${openApi.serviceKey}")
    private String serviceKey;

    /**
     * 사업자등록번호(필수)	숫자로 이루어진 10자리 값만 가능 ('-' 등의 기호 반드시 제거 후 호출) 대표자성명(필수)	외국인 사업자의 경우에는 영문명 입력 개업일자(필수) YYYYMMDD
     */
    public void connection(MartCertificationRequestDto requestDto) {
        try {
            System.out.println("여기오는지만 확인해줄ㄹ ㅐ..");
            URL url = new URL("https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=" + serviceKey);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //  URL연결은 입출력에 사용 될 수 있고, POST 혹은 PUT 요청을 하려면 setDoOutput을 true로 설정해야함.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            //	POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            // https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=xxxxxx
            // b_no: 사업자 등록번호
            // start_dt
            // p_nm

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            //
            //            ObjectMapper objectMapper = new ObjectMapper();
            //
            //            List<String> arr = new ArrayList<String>();
            //            arr.add("1081921785");
            //
            //            JSONObject obj1 = new JSONObject();
            //            obj1.put("b_no", arr);
            //
            //            String jsonString = objectMapper.writeValueAsString(obj1);

            wr.write("b_no=" + requestDto.getBNo()); //json 형식의 message 전달
            wr.write("&start_dt=" + requestDto.getStartDt()); //json 형식의 message 전달
            wr.write("&p_nm=" + requestDto.getPNm()); //json 형식의 message 전달
            wr.flush();

            conn = (HttpURLConnection) url.openConnection();
            //conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            System.out.println(conn.getResponseMessage());
            if (responseCode == 400 || responseCode == 401 || responseCode == 500) {
                System.out.println(responseCode + " Error!");
            } else {
                //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                String result = "";

                while ((line = br.readLine()) != null) {
                    result += line;
                }
                System.out.println("response body : " + result);

                //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);

                // access_Token = element.getAsJsonObject().get("access_token").getAsString();
                // refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

                // System.out.println("refresh_token : " + refresh_Token);

                br.close();
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
