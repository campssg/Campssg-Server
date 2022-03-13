package com.campssg.service;

import com.campssg.DB.entity.Cart;
import com.campssg.DB.entity.User;
import com.campssg.DB.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void 장바구니_조회() {
        Cart cart = cartRepository.findByUser_userId(14L).orElseGet(Cart::new);
        if (cart.getUser() == null) {
            cartRepository.save(Cart.builder().user(new User(14L)).build()); // 없을 경우 장바구니 등록
        }
        System.out.println(cart);
    }
}
