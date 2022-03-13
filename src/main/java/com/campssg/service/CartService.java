package com.campssg.service;

import com.campssg.DB.entity.*;
import com.campssg.DB.repository.CartItemRepository;
import com.campssg.DB.repository.CartRepository;
import com.campssg.DB.repository.ProductRepository;
import com.campssg.DB.repository.UserRepository;
import com.campssg.dto.cart.CartInfoResponseDto;
import com.campssg.dto.cart.CartItemRequestDto;
import com.campssg.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

     public CartInfoResponseDto getCartInfo() {
         User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow(); // 현재 로그인하고 있는 사용자 정보 가져오기
         Cart cart = cartRepository.findByUser_userId(user.getUserId()).orElseGet(Cart::new); // 사용자 아이디 값으로 장바구니 가져오기
         if (cart.getCartId() == null) {
             cartRepository.save(Cart.builder().user(user).build()); // 없을 경우 장바구니 등록
         }
         return new CartInfoResponseDto(cart);
     }
}
