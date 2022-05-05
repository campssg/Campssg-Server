package com.campssg.service;

import com.campssg.DB.entity.*;
import com.campssg.DB.repository.CartItemRepository;
import com.campssg.DB.repository.CartRepository;
import com.campssg.DB.repository.MartRepository;
import com.campssg.DB.repository.ProductRepository;
import com.campssg.DB.repository.UserRepository;
import com.campssg.dto.cart.CanAddDto;
import com.campssg.dto.cart.CartComparisonListResponseDto;
import com.campssg.dto.cart.CartComparisonListResponseDto.CartComparison;
import com.campssg.dto.cart.CartInfoResponseDto;
import com.campssg.dto.cart.AddCartItemRequestDto;
import com.campssg.dto.cart.CartInfoResponseDto.CartItemList;
import com.campssg.util.SecurityUtil;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final MartRepository martRepository;

    // 장바구니 추가
    public Cart addCart() {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow(); // 현재 로그인하고 있는 사용자 정보 가져오기
        Cart cart = cartRepository.save(Cart.builder().user(user).totalCount(0).totalPrice(0).build());
        return cart;
    }

    // 내 장바구니 정보 확인 - 담겨 있는 상품 목록, 총 개수, 총 가격
     public CartInfoResponseDto getCartInfo() {
         User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow(); // 현재 로그인하고 있는 사용자 정보 가져오기
         Cart cart = cartRepository.findByUser_userId(user.getUserId()).orElseGet(Cart::new); // 사용자 아이디 값으로 장바구니 가져오기
         if (cart.getCartId() == null) {
             cartRepository.save(Cart.builder().user(user).totalCount(0).totalPrice(0).build()); // 없을 경우 장바구니 등록
         }
         List<CartItem> cartItemList = cartItemRepository.findByCart_cartId(cart.getCartId()); // 장바구니에 있는 상품 목록 가져오기
         Long martId = cartItemList.get(0).getProduct().getMart().getMartId();
         List<CartInfoResponseDto.CartItemList> cartItemLists = cartItemList.stream()
                 .map(cartItem -> new CartInfoResponseDto().new CartItemList(cartItem)).collect(Collectors.toList());
         return new CartInfoResponseDto(cart, cartItemLists, martId);
     }

     // 장바구니에 상품 담기
     public void addCartItem(Long martId, Long productId, AddCartItemRequestDto requestDto) {
         User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow(); // 현재 로그인하고 있는 사용자 정보 가져오기
         Cart cart = cartRepository.findByUser_userId(user.getUserId()).orElseGet(this::addCart); // 사용자 아이디 값으로 장바구니 가져오기
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

     // 장바구니에 있는 상품과 새로 추가하려는 상품이 다를 경우 - 기존 장바구니 삭제 후 새로 생성하고 담기
    public void addNewCartItem(Long productId, AddCartItemRequestDto requestDto) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow(); // 현재 로그인하고 있는 사용자 정보 가져오기
        Cart cart = deleteCart(user.getUserId());
        Product product = productRepository.findByProductId(productId);
        CartItem cartItem = CartItem.builder().cart(cart).product(product).cartItemCount(requestDto.getCount()).build();
        cartItemRepository.save(cartItem);
        cart.addTotalCount(requestDto.getCount());
        cart.addTotalPrice(requestDto.getCount(), cartItem.getProduct().getProductPrice());
    }

     // 장바구니에 있는 상품 삭제
     public void deleteCartItem(Long cartItemId) {
         CartItem cartItem = cartItemRepository.findByCartItemId(cartItemId);
         if (cartItem != null) { // 장바구니에 상품이 있을 경우 삭제
             Cart cart = cartItem.getCart();
             cart.subTotalCount(cartItem.getCartItemCount());
             cart.subTotalPrice(cartItem.getCartItemCount(), cartItem.getProduct().getProductPrice());
             cartItemRepository.delete(cartItem);
         }
     }

     // 장바구니 아예 삭제하고 다시 생성
    public Cart deleteCart(Long userId) {
        Cart cart = cartRepository.findByUser_userId(userId).orElseThrow();
        cartRepository.delete(cart);
        return addCart();
    }

    // 장바구니 아이템과 새로 추가하려는 아이템이 같은 마트 상품인지 확인
    public CanAddDto canAdd(Long martId) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        Cart cart = cartRepository.findByUser_userId(user.getUserId()).orElseThrow();
        List<CartItem> existItem = cartItemRepository.findByCart_cartId(cart.getCartId());
        if (existItem.isEmpty()) {
            return new CanAddDto(1L);
        } else {
            if (existItem.get(0).getProduct().getMart().getMartId() == martId) {
                return new CanAddDto(1L);
            } else {
                return new CanAddDto(0L);
            }
        }
    }

    public CartComparisonListResponseDto getCartComparison(Double latitude, Double longitude) {
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUserEmail).orElseThrow();
        Cart cart = cartRepository.findByUser_userId(user.getUserId()).orElseThrow();
        List<CartComparisonListResponseDto.CartComparison> responseDto = new ArrayList<>();
        // 현재 주변 마트 리스트
        List<Mart> aroundMart = martRepository.findAroundMart(latitude, longitude);
        // 장바구니 상품 목록 불러오기
        List<CartItem> cartList = cartItemRepository.findByCart_cartId(cart.getCartId());
        for (Mart mart : aroundMart) { // 주변 마트 검색
            int totalPrice = 0;
            int notExistCnt = 0;
            int notExistTotalCnt = 0;
            for (CartItem cartItem : cartList) {
                // 장바구니 상품 이름으로 마트에 해당 상품이 존재하는지 찾기
                Product ExistsProduct = productRepository.findByProductNameAndMart_martId(cartItem.getProduct().getProductName(), mart.getMartId());
                if (ExistsProduct != null) { // 존재할 경우 해당 마트에서 판매하는 가격 추가
                    totalPrice += ExistsProduct.getProductPrice() * cartItem.getCartItemCount();
                } else { // 존재하지 않을 경우 존재하지 않는 상품 개수 추가
                    notExistCnt += 1; // 존재하지 않는 상품 종류 개수
                    notExistTotalCnt += cartItem.getCartItemCount(); // 존재하지 않는 상품 전체 개수
                }
            }
            responseDto.add(new CartComparison(mart, notExistCnt, notExistTotalCnt, totalPrice));
        }
        return new CartComparisonListResponseDto(responseDto);
    }
}
