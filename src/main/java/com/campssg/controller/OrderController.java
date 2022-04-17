package com.campssg.controller;

import com.campssg.dto.order.MartOrderListResponseDto;
import com.campssg.dto.order.OrderRequestDto;
import com.campssg.dto.order.OrderResponseDto;
import com.campssg.dto.order.UserOrderListResponseDto;
import com.campssg.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
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
    public OrderResponseDto addOrderInfo(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        return orderService.addOrderInfo(orderRequestDto);
    }

    @ApiOperation(value = "주문 상세 내역 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "주문 상세 내역 조회 성공")
    })
    @GetMapping("/{orderId}")
    public OrderResponseDto getOrderInfo(@PathVariable Long orderId) {
        return orderService.getOrderInfo(orderId);
    }

    @ApiOperation(value = "사용자 주문 내역 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "사용자 주문 내역 조회 성공")
    })
    @GetMapping("/user")
    public List<UserOrderListResponseDto> getUserOrderList() {
        return orderService.getUserOrderList();
    }

    @ApiOperation(value = "마트가 하나인 마트 운영자 주문 현황 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "마트가 하나인 마트 운영자 주문 현황 조회 성공")
    })
    @GetMapping("/mart")
    public List<MartOrderListResponseDto> getMartOrderList() {
        return orderService.getMartOrderList();
    }

    @ApiOperation(value = "마트별 주문 현황 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "마트별 주문 현황 조회 성공")
    })
    @GetMapping("/mart/{martId}")
    public List<MartOrderListResponseDto> getMartOrderList(@PathVariable Long martId) {
        return orderService.getMartOrderList(martId);
    }
}
