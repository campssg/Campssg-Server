package com.campssg.dto.camping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

@Getter
@NoArgsConstructor
public class CampingResponseDto {
    private String campName;
    private String address;
    private String detailAddress;
    private String mapX;
    private String mapY;
    private String homepage;
    private String img;

    public CampingResponseDto(JSONObject object) {
        if (object.containsKey("facltNm")) this.campName = object.get("facltNm").toString();
        if (object.containsKey("addr1")) this.address = object.get("addr1").toString();
        if (object.containsKey("addr2")) this.detailAddress = object.get("addr2").toString();
        if (object.containsKey("mapX")) this.mapX = object.get("mapX").toString();
        if (object.containsKey("mapY")) this.mapY = object.get("mapY").toString();
        if (object.containsKey("homepage")) this.homepage = object.get("homepage").toString();
        if (object.containsKey("firstImageUrl")) this.img = object.get("firstImageUrl").toString();
    }
}
