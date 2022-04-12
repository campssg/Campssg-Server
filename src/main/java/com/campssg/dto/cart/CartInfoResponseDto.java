package com.campssg.dto.cart;

import com.campssg.DB.entity.Cart;
import com.campssg.DB.entity.CartItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CartInfoResponseDto<CartItemList> {
    private Long cartId;
    private int totalCount;
    private int totalPrice;
    private List<CartItemList> cartItemList;

    public CartInfoResponseDto(Cart cart, List<CartItemList> cartItemLists) {
        this.cartId = cart.getCartId();
        this.totalCount = cart.getTotalCount();
        this.totalPrice = cart.getTotalPrice();
        this.cartItemList = cartItemLists;
    }

    @Getter
    public class CartItemList {
        private Long cartItemId;
        private String cartItemName;
        private int cartItemPrice;
        private int cartItemCount;

        public CartItemList(CartItem cartItem) {
            this.cartItemId = cartItem.getCartItemId();
            this.cartItemName = cartItem.getProduct().getProductName();
            this.cartItemPrice = cartItem.getProduct().getProductPrice();
            this.cartItemCount = cartItem.getCartItemCount();
        }
    }
}
