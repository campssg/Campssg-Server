package com.campssg.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemRequestDto {
    private Integer count;
    private Integer price;
}
