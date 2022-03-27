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
        private String martImg;
        private String martName;
        private int notExistsCnt;
        private int totalPrice;

        public CartComparison(Mart mart, int notExistsCnt, int totalPrice) {
            this.martImg = "";
            this.martName = mart.getMartName();
            this.notExistsCnt = notExistsCnt;
            this.totalPrice = totalPrice;
        }
    }
}
