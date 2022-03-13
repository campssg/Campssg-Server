package com.campssg.service;

import com.campssg.DB.entity.Cart;
import com.campssg.DB.entity.User;
import com.campssg.DB.repository.CartRepository;
import com.campssg.DB.repository.UserRepository;
import com.campssg.dto.cart.CartListResponseDto;
import com.campssg.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

     public void addCart() {
         User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow(); // 현재 로그인하고 있는 사용자 정보 가져오기
         cartRepository.save(Cart.builder().user(user).build()); // 장바구니 등록
     }

     public List<CartListResponseDto> getCartList() {
         User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow(); // 현재 로그인하고 있는 사용자 정보 가져오기
         List<Cart> cartList = cartRepository.findByUser_userId(user.getUserId()); // 사용자 아이디 값으로 장바구니 목록 가져오기
         return cartList.stream().map(CartListResponseDto::new).collect(Collectors.toList());
     }
}
