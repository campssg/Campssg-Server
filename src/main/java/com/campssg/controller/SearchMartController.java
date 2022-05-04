package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.cart.AddCartItemRequestDto;
import com.campssg.dto.cart.CanAddDto;
import com.campssg.dto.mart.MartListResponseDto;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search/mart")
public class SearchMartController {

    private final CartService cartService;
    private final MartService martService;

    @ApiOperation(value = "위치 기반으로 마트 조회")
    @ApiResponses(
            @ApiResponse(code = 200, message = "위치 기반 마트 조회 완료")
    )
    @GetMapping("/{latitude}/{longitude}")
    public ResponseEntity<List<MartListResponseDto>> searchAroundMartList(
            @PathVariable Double latitude, @PathVariable Double longitude) {
        return ResponseEntity.ok(martService.searchAroundMart(latitude, longitude));
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

    @ApiOperation(value = "마트에 있는 상품 카테고리 별로 조회")
    @ApiResponses(
            @ApiResponse(code = 200, message = "카테고리별 상품 목록 조회 완료")
    )
    @GetMapping("/category/{categoryId}/{martId}")
    public ResponseEntity<ProductListResponse> searchCategoryItem(@PathVariable Long categoryId, @PathVariable Long martId) {
        return ResponseEntity.ok(martService.findProductByCategory(martId, categoryId));
    }

    @ApiOperation(value = "마트 상품 장바구니에 추가")
    @ApiResponses(
            @ApiResponse(code = 200, message = "마트 상품 장바구니에 추가 완료")
    )
    @PostMapping("/{martId}/{productId}")
    public ResponseEntity<ResponseMessage> addCartItem(@PathVariable Long martId, @PathVariable Long productId, @RequestBody AddCartItemRequestDto cartItemRequestDto) {
        cartService.addCartItem(martId, productId, cartItemRequestDto);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "success"));
    }

    @ApiOperation(value = "마트 상품을 장바구니에 추가할 수 있는지 확인")
    @ApiResponses(
            @ApiResponse(code = 200, message = "추가 가능 여부 확인 완료")
    )
    @GetMapping("/canAdd/{martId}")
    public ResponseEntity<CanAddDto> canAdd(@PathVariable Long martId) {
        return ResponseEntity.ok(cartService.canAdd(martId));
    }

    @ApiOperation(value = "기존 장바구니 삭제하고 새로운 장바구니에 상품 추가")
    @ApiResponses(
            @ApiResponse(code = 200, message = "기존 장바구니 삭제, 새로운 장바구니에 상품 추가 완료")
    )
    @PostMapping("/new/{productId}")
    public ResponseEntity<ResponseMessage> addNewCartItem(@PathVariable Long productId, @RequestBody AddCartItemRequestDto cartItemRequestDto) {
        cartService.addNewCartItem(productId, cartItemRequestDto);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "success"));
    }
}
