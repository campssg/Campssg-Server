package com.campssg.dto.cart;

import com.campssg.DB.entity.Cart;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartListResponseDto {
    private Long cartId;

    public CartListResponseDto(Cart cart) {
        this.cartId = cart.getCartId();
    }
}
