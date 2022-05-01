package com.campssg.dto.requestedProduct;

import com.campssg.DB.entity.RequestedProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GuestRequestDto {
    private Long requestedProductId;
    private String requestedProductName;
    private int requestedProductPrice;
    private int requestedProductCount;

    public GuestRequestDto(RequestedProduct requestedProduct) {
        this.requestedProductId = requestedProduct.getRequestedProductId();
        this.requestedProductName = requestedProduct.getRequestedProductName();
        this.requestedProductPrice = requestedProduct.getRequestedProductPrice();
        this.requestedProductCount = requestedProduct.getRequestedProductCount();
    }
}
