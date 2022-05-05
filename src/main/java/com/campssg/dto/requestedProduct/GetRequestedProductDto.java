package com.campssg.dto.requestedProduct;

import com.campssg.DB.entity.RequestedProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetRequestedProductDto {
    private Long requestedProductId;
    private String requestedProductName;
    private int requestedProductPrice;
    private int requestedProductCount;
    private String requestedProductState;
    private String requestedProductReference;
    private String martName;

    public GetRequestedProductDto(RequestedProduct requestedProduct) {
        this.martName = requestedProduct.getMart().getMartName();
        this.requestedProductId = requestedProduct.getRequestedProductId();
        this.requestedProductName = requestedProduct.getRequestedProductName();
        this.requestedProductPrice = requestedProduct.getRequestedProductPrice();
        this.requestedProductCount = requestedProduct.getRequestedProductCount();
        this.requestedProductState = requestedProduct.getRequestedProductState().toString();
        this.requestedProductReference = requestedProduct.getRequestedProductReference();
    }
}
