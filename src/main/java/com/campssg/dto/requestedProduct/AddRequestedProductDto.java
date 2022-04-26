package com.campssg.dto.requestedProduct;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddRequestedProductDto {
    private Long orderId;
    private String requestedProductName;
    private int requestedProductPrice;
    private int requestedProductCount;
    private String requestedProductReference;
}
