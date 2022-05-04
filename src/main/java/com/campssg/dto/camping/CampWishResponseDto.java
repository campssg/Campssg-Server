package com.campssg.dto.camping;

import com.campssg.DB.entity.CampWishlist;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CampWishResponseDto {
    private String campName;
    private String campTel;
    private String campAddress;
    private Double latitude;
    private Double longitude;

    public CampWishResponseDto(CampWishlist campWishlist) {
        this.campName = campWishlist.getCampName();
        this.campTel = campWishlist.getCampTel();
        this.campAddress = campWishlist.getCampAddress();
        this.latitude = campWishlist.getCampLatitude();
        this.longitude = campWishlist.getCampLongitude();
    }
}
