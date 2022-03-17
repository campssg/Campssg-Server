package com.campssg.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCartItemRequestDto {
    private int count; // 장바구니에 추가할 물품 수량
}
