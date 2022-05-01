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

    public MartListResponseDto(Mart mart) {
        this.martId = mart.getMartId();
        this.martName = mart.getMartName();
        this.martAddress = mart.getMartAddress();
        this.openTime = mart.getOpenTime();
        this.closeTime = mart.getCloseTime();
        this.latitude = mart.getLatitude();
        this.longitude = mart.getLongitude();
        this.requestYn = mart.getRequestYn();
    }
}
