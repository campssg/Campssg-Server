package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.requestedProduct.AddRequestedProductDto;
import com.campssg.dto.requestedProduct.GetRequestedProductDto;
import com.campssg.dto.requestedProduct.GuestRequestDto;
import com.campssg.service.RequestedProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestedProductController {

    private final RequestedProductService requestedProductService;

    @ApiOperation(value = "새로운 요청 상품 추가")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "요청 상품 추가 완료")
    })
    @PostMapping("/add")
    public ResponseEntity<GuestRequestDto> guestRequestProduct(@Valid @RequestBody AddRequestedProductDto addRequestedProductDto) throws IOException {
        return ResponseEntity.ok(requestedProductService.guestRequestProduct(addRequestedProductDto));
    }

    @ApiOperation(value = "마트에서 서비스 이용자가 가격요청중인 요청 상품 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "가격요청중인 요청 상품 조회 완료")
    })
    @GetMapping("/mart/{martId}")
    public ResponseEntity<List<GetRequestedProductDto>> getRequestedProduct(@PathVariable Long martId) {
        return ResponseEntity.ok(requestedProductService.getRequestedProductFromMart(martId));
    }

    @ApiOperation(value = "서비스 이용자가 마트에서 가격제시중인 요청 상품 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "가격제시중인 요청 상품 조회 완료")
    })
    @GetMapping("/guest")
    public ResponseEntity<List<GetRequestedProductDto>> getRequestedProduct() {
        return ResponseEntity.ok(requestedProductService.getRequestedProductFromGuest());
    }

    @ApiOperation(value = "가격 승인하고 흥정 완료 처리")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "흥정 완료 처리 성공")
    })
    @PostMapping("/accept/{requestedProductId}")
    public ResponseEntity<ResponseMessage> acceptRequest(@PathVariable Long requestedProductId) {
        requestedProductService.acceptedProduct(requestedProductId);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "흥정이 완료되었습니다"));
    }

    @ApiOperation(value = "마트운영자 측에서 서비스 이용자에게 가격 제시")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "가격 제시 완료")
    })
    @PostMapping("/price/mart/{requestedProductId}/{price}")
    public ResponseEntity<ResponseMessage> requestPriceToGuest(@PathVariable Long requestedProductId, @PathVariable int price) {
        requestedProductService.requestPriceToGuest(requestedProductId, price);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "가격 제시가 완료되었습니다"));
    }

    @ApiOperation(value = "서비스 이용자 측에서 마트 운영자에게 가격 요청")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "가격 요청 완료")
    })
    @PostMapping("/price/guest/{requestedProductId}/{price}")
    public ResponseEntity<ResponseMessage> requestPriceToMart(@PathVariable Long requestedProductId, @PathVariable int price) {
        requestedProductService.requestPriceToMart(requestedProductId, price);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "가격 요청이 완료되었습니다"));
    }

    @ApiOperation(value = "마트 운영자 전체 요청 상품 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "전체 요청 상품 조회 완료")
    })
    @GetMapping("/total/{martId}")
    public ResponseEntity<List<GetRequestedProductDto>> getTotalRequestedProductFromMart(@PathVariable Long martId) {
        return ResponseEntity.ok(requestedProductService.getTotalRequestedProductFromMart(martId));
    }

    @ApiOperation(value = "서비스 이용자 전체 요청 상품 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "전체 요청 상품 조회 완료")
    })
    @GetMapping("/total/guest")
    public ResponseEntity<List<GetRequestedProductDto>> getTotalRequestedProductFromGuest() {
        return ResponseEntity.ok(requestedProductService.getTotalRequestedProductFromGuest());
    }
}
