package com.campssg.dto.mart;

import com.campssg.DB.entity.Mart;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "마트 리스트 조회 응답값")
public class MartListResponseDto {

    private String martName;
    private String martAddress;
    private String openTime;
    private String closeTime;
    private String number;
    private Long requestYn;

    public MartListResponseDto(Mart mart) {
        this.martName = mart.getMartName();
        this.martAddress = mart.getMartAddress();
        this.openTime = mart.getOpenTime();
        this.closeTime = mart.getCloseTime();
        this.requestYn = mart.getRequestYn();
    }
}
