package com.campssg.dto.mart;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "마트 리스트 조회 응답값")
public class MartListResponseDto {

    private String martName;
    private Long userId;
    private String martAddress;
    private String openTime;
    private String closeTime;
    private String number;
    private Long requestYn;
}
