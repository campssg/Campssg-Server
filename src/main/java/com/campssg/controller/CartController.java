package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.cart.CartComparisonListResponseDto;
import com.campssg.dto.cart.CartInfoResponseDto;
import com.campssg.service.CartService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @ApiOperation(value = "장바구니 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "장바구니 조회 성공")
    })
    @GetMapping("/info")
    public ResponseEntity<ResponseMessage<CartInfoResponseDto>> getCart() {
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "success", cartService.getCartInfo()), HttpStatus.OK);
    }

    @ApiOperation(value = "장바구니 물품 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "장바구니 물품 삭제 성공")
    })
    @PostMapping("/delete/{cartItemId}")
    public ResponseEntity<ResponseMessage> deleteCartItem(@PathVariable Long cartItemId) {
        cartService.deleteCartItem(cartItemId);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "삭제가 완료되었습니다"));
    }

    @ApiOperation(value = "현재 위치 기반 마트 주변에 장바구니 재고 및 가격비교")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "장바구니 가격 비교")
    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "latitude", value = "현재 위치의 위도", required = true, paramType = "path"),
        @ApiImplicitParam(name = "latitude", value = "현재 위치의 경도", required = true, paramType = "path")
    })
    @GetMapping("/{latitude}/{longitude}")
    public ResponseEntity<ResponseMessage<CartComparisonListResponseDto>> getCartComparison(@PathVariable Double latitude, @PathVariable Double longitude) {
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "success", cartService.getCartComparison(latitude, longitude)), HttpStatus.OK);
    }
}
