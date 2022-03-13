package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.cart.CartListResponseDto;
import com.campssg.service.CartService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @ApiOperation(value = "장바구니 등록")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "장바구니 등록 성공")
    })
    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> addCart() {
        cartService.addCart();
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "success"));
    }

    @ApiOperation(value = "장바구니 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "장바구니 목록 조회 성공")
    })
    @GetMapping("/list")
    public ResponseEntity<ResponseMessage<List<CartListResponseDto>>> getCartList() {
        List<CartListResponseDto> cartList = cartService.getCartList();
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "success", cartList), HttpStatus.OK);
    }
}
