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

        public CartComparison(Mart mart, int notExistsCnt, int notExistTotalcnt, int totalPrice) {
            this.martId = mart.getMartId();
            this.martAddress = mart.getMartAddress();
            this.openTime = mart.getOpenTime();
            this.closeTime = mart.getCloseTime();
            this.martImg = "";
            this.martName = mart.getMartName();
            this.notExistsCnt = notExistsCnt;
            this.notExistTotalcnt = notExistTotalcnt;
            this.totalPrice = totalPrice;
        }
    }
}
