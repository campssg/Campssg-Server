package com.campssg.dto.camping;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CampWishRequestDto {
    private String campName;
    private String campTel;
    private String campAddress;
    private Double latitude;
    private Double longitude;
}
