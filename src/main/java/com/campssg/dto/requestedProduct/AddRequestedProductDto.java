package com.campssg.dto.requestedProduct;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddRequestedProductDto {

    @NotNull
    private Long orderId;

    @NotNull
    private String requestedProductName;

    @NotNull
    private int requestedProductPrice;

    @NotNull
    private int requestedProductCount;

    @NotNull
    private String requestedProductReference;
}
