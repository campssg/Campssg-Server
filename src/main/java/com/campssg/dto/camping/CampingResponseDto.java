package com.campssg.dto.camping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;

@Getter
@Setter
@NoArgsConstructor
public class CampingResponseDto {
    private String facltNm;
    private String addr1;
    private String addr2;
    private String mapX;
    private String mapY;
    private String tel;
    private String homepage;
    private String img;

    public CampingResponseDto(JSONObject object) {
        this.facltNm = object.get("facltNm").toString();
        this.addr1 = object.get("addr1").toString();
        if (object.get("addr2").toString() != null) {
            this.addr2 = object.get("addr2").toString();
        }
        this.mapX = object.get("mapX").toString();
        this.mapY = object.get("mapY").toString();
        this.tel = object.get("tel").toString();
        this.homepage = object.get("homepage").toString();
        this.img = object.get("firstImageUrl").toString();
    }
}
