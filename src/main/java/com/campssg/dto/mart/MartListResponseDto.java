package com.campssg.dto.mart;

import com.campssg.DB.entity.Mart;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "마트 리스트 조회 응답값")
public class MartListResponseDto {

    private Long martId;
    private String martName;
    private String martAddress;
    private String openTime;
    private String closeTime;
    private Double latitude;
    private Double longitude;
    private Long requestYn;
    private Double distance;
    private String martImg;

    public MartListResponseDto(Mart mart, Double latitude, Double longitude) {
        this.martId = mart.getMartId();
        this.martName = mart.getMartName();
        this.martAddress = mart.getMartAddress();
        this.openTime = mart.getOpenTime();
        this.closeTime = mart.getCloseTime();
        this.latitude = mart.getLatitude();
        this.longitude = mart.getLongitude();
        this.martImg = mart.getMartImg();
        this.requestYn = mart.getRequestYn();
        if (latitude != null && longitude != null) {
            this.distance = distance(mart.getLatitude(), mart.getLongitude(), latitude, longitude);
        }
    }

    private Double distance(Double lat1, Double long1, Double lat2, Double long2) {
        double theta = long1 - long2;
        double dist = Math.sin(lat1 * Math.PI / 180.0) * Math.sin(lat2 * Math.PI / 180.0)
                + Math.cos(lat1*Math.PI/180.0)*Math.cos(lat2*Math.PI/180.0)*Math.cos(theta*Math.PI/180.0);
        dist = Math.acos(dist);
        dist = dist*180/Math.PI;
        dist = dist * 60 *1.1515*1609.344;
        return dist;
    }
}
