package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.cart.CartInfoResponseDto;
import com.campssg.service.CartService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
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
}
