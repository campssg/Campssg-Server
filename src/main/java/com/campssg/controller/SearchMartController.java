package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.cart.CartItemRequestDto;
import com.campssg.dto.mart.ProductListResponse;
import com.campssg.service.CartService;
import com.campssg.service.MartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search/mart")
public class SearchMartController {

    private final CartService cartService;
    private final MartService martService;

    @GetMapping("")
    public void searchMartList() {
        // TODO: 위치 정보 기반으로 마트 검색
    }

    @GetMapping("/{martId}")
    public ResponseEntity<ResponseMessage<ProductListResponse>> searchMart(@PathVariable Long martId) {
        // 검색한 마트에 있는 상품 목록 불러오기
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "마트 상품 조회 성공", martService.findProductByMartId(martId)), HttpStatus.OK);
    }

    // 마트에 있는 상품 장바구니에 등록
    @PostMapping("/{martId}/{productId}")
    public ResponseEntity<ResponseMessage> addCartItem(@PathVariable Long productId, @RequestBody CartItemRequestDto cartItemRequestDto) {
        cartService.addCartItem(productId, cartItemRequestDto);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "success"));
    }
}
