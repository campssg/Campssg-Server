package com.campssg.controller;

import com.campssg.dto.order.OrderRequestDto;
import com.campssg.dto.order.OrderResponseDto;
import com.campssg.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @ApiOperation(value = "주문서 생성")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "주문서 생성 성공")
    })
    @PostMapping("/info")
    public OrderResponseDto getOrderInfo(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        return orderService.getOrderInfo(orderRequestDto);
    }
}
