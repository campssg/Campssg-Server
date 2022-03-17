package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.cart.AddCartItemRequestDto;
import com.campssg.dto.mart.ProductListResponse;
import com.campssg.service.CartService;
import com.campssg.service.MartService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "마트에 있는 상품 목록 조회")
    @ApiResponses(
            @ApiResponse(code = 200, message = "마트 상품 목록 조회 완료")
    )
    @GetMapping("/{martId}")
    public ResponseEntity<ResponseMessage<ProductListResponse>> searchMart(@PathVariable Long martId) {
        // 검색한 마트에 있는 상품 목록 불러오기
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "success", martService.findProductByMartId(martId)), HttpStatus.OK);
    }

    @ApiOperation(value = "마트 상품 장바구니에 추가")
    @ApiResponses(
            @ApiResponse(code = 200, message = "마트 상품 장바구니에 추가 완료")
    )
    @PostMapping("/{martId}/{productId}")
    public ResponseEntity<ResponseMessage> addCartItem(@PathVariable Long productId, @RequestBody AddCartItemRequestDto cartItemRequestDto) {
        cartService.addCartItem(productId, cartItemRequestDto);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "success"));
    }
}
