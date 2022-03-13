package com.campssg.dto.cart;

import com.campssg.DB.entity.Cart;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartInfoResponseDto {
    private Long cartId;
    private int totalCount;
    private Long totalPrice;

    public CartInfoResponseDto(Cart cart) {
        this.cartId = cart.getCartId();
    }
}
