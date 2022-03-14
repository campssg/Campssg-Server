package com.campssg.service;

import com.campssg.DB.entity.Cart;
import com.campssg.DB.entity.CartItem;
import com.campssg.DB.entity.Product;
import com.campssg.DB.entity.User;
import com.campssg.DB.repository.CartItemRepository;
import com.campssg.DB.repository.CartRepository;
import com.campssg.DB.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    public void 장바구니_조회() {
        Cart cart = cartRepository.findByUser_userId(14L).orElseGet(Cart::new);
        if (cart.getUser() == null) {
            cartRepository.save(Cart.builder().user(new User(14L)).build()); // 없을 경우 장바구니 등록
        }
        System.out.println(cart.getCartId());
    }

    @Test
    public void 장바구니_상품_조회() {
        CartItem cartItem = cartItemRepository.findByCart_cartIdAndProduct_productId(2L, 1L);
        if (cartItem == null) {
            System.out.println("없음");
        } else {
            System.out.println("있음");
        }
    }
}
