package com.campssg.dto.cart;

import com.campssg.DB.entity.Mart;
import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "장바구니 비교 응답 값")
public class CartComparisonListResponseDto {
    List<CartComparison> cartComparisonList;

    public CartComparisonListResponseDto(List<CartComparison> cartComparisonList) {
        this.cartComparisonList = cartComparisonList;
    }

    @Getter
    @NoArgsConstructor
    public static class CartComparison {
        private Long martId;
        private String martImg;
        private String martName;
        private String martAddress;
        private String openTime;
        private String closeTime;
        private int notExistsCnt;
        private int notExistTotalcnt;
        private int totalPrice;
        private Double distance;

        public CartComparison(Mart mart, int notExistsCnt, int notExistTotalcnt, int totalPrice, Double latitude, Double longitude) {
            this.martId = mart.getMartId();
            this.martAddress = mart.getMartAddress();
            this.openTime = mart.getOpenTime();
            this.closeTime = mart.getCloseTime();
            this.martImg = "";
            this.martName = mart.getMartName();
            this.notExistsCnt = notExistsCnt;
            this.notExistTotalcnt = notExistTotalcnt;
            this.totalPrice = totalPrice;
            this.distance = distance(mart.getLatitude(), mart.getLongitude(), latitude, longitude);
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
}
