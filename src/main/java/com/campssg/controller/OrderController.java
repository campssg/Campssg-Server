package com.campssg.controller;

import com.campssg.dto.order.*;
import com.campssg.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @ApiOperation(value = "주문서 생성")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "주문서 생성 성공")
    })
    @PostMapping("/add")
    public ResponseEntity<OrderResponseDto> addOrderInfo(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.addOrderInfo(orderRequestDto));
    }

    @ApiOperation(value = "주문 상세 내역 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "주문 상세 내역 조회 성공")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponseDto> getOrderInfo(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderInfo(orderId));
    }

    @ApiOperation(value = "사용자 주문 내역 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "사용자 주문 내역 조회 성공")
    })
    @GetMapping("/user")
    public ResponseEntity<List<UserOrderListResponseDto>> getUserOrderList() {
        return ResponseEntity.ok(orderService.getUserOrderList());
    }

    @ApiOperation(value = "마트별 주문 현황 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "마트별 주문 현황 조회 성공")
    })
    @GetMapping("/mart/{martId}")
    public ResponseEntity<List<MartOrderListResponseDto>> getMartOrderList(@PathVariable Long martId) {
        return ResponseEntity.ok(orderService.getMartOrderList(martId));
    }

    @ApiOperation(value = "픽업준비완료된 주문 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "픽업준비완료된 주문 조회 성공")
    })
    @GetMapping("/prepared")
    public ResponseEntity<List<UserOrderListResponseDto>> getPreparedOrderList() {
        return ResponseEntity.ok(orderService.getPreparedOrderList());
    }
}
