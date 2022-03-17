package com.campssg.service;

import com.campssg.DB.entity.*;
import com.campssg.DB.repository.CartItemRepository;
import com.campssg.DB.repository.CartRepository;
import com.campssg.DB.repository.ProductRepository;
import com.campssg.DB.repository.UserRepository;
import com.campssg.dto.cart.CartInfoResponseDto;
import com.campssg.dto.cart.AddCartItemRequestDto;
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
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    // 내 장바구니 정보 확인 - 담겨 있는 상품 목록, 총 개수, 총 가격
     public CartInfoResponseDto getCartInfo() {
         User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow(); // 현재 로그인하고 있는 사용자 정보 가져오기
         Cart cart = cartRepository.findByUser_userId(user.getUserId()).orElseGet(Cart::new); // 사용자 아이디 값으로 장바구니 가져오기
         if (cart.getCartId() == null) {
             System.out.println("없음");
             cartRepository.save(Cart.builder().user(user).totalCount(0).totalPrice(0).build()); // 없을 경우 장바구니 등록
         }

         List<CartItem> cartItemList = cartItemRepository.findByCart_cartId(cart.getCartId()); // 장바구니에 있는 상품 목록 가져오기
         List<CartInfoResponseDto.CartItemList> cartItemLists = cartItemList.stream()
                 .map(cartItem -> new CartInfoResponseDto().new CartItemList(cartItem)).collect(Collectors.toList());
         return new CartInfoResponseDto(cart, cartItemLists);
     }

     // 장바구니에 상품 담기
     public void addCartItem(Long productId, AddCartItemRequestDto requestDto) {
         User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow(); // 현재 로그인하고 있는 사용자 정보 가져오기
         Cart cart = cartRepository.findByUser_userId(user.getUserId()).orElseGet(Cart::new); // 사용자 아이디 값으로 장바구니 가져오기
         if (cart.getCartId() == null) {
             cartRepository.save(Cart.builder().user(user).totalCount(0).totalPrice(0).build()); // 없을 경우 장바구니 등록
         }

         Product product = productRepository.findByProductId(productId); // 상품 아이디로 상품 정보 가져오기
         CartItem cartItem = cartItemRepository.findByCart_cartIdAndProduct_productId(cart.getCartId(), productId); // 장바구니 안에 이미 있는 상품인지 확인

         if (cartItem == null) { // 장바구니 안에 없는 상품일 경우 새로 추가
             cartItem = CartItem.builder()
                     .cart(cart)
                     .product(product)
                     .cartItemCount(requestDto.getCount())
                     .build();
             cartItemRepository.save(cartItem);
         } else { // 장바구니 안에 이미 있는 상품일 경우 수량만 증가
             cartItem.addCount(requestDto.getCount());
             cartItemRepository.save(cartItem);
         }

         cart.addTotalCount(requestDto.getCount()); // 장바구니 상품 총 개수 증가
         cart.addTotalPrice(requestDto.getCount(), cartItem.getProduct().getProductPrice()); // 장바구니 상품 총 가격 증가
     }
}