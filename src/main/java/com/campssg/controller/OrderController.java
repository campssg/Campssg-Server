package com.campssg.controller;

import com.campssg.DB.entity.OrderState;
import com.campssg.dto.ResponseMessage;
import com.campssg.dto.order.MartOrderListResponseDto;
import com.campssg.dto.order.OrderRequestDto;
import com.campssg.dto.order.OrderResponseDto;
import com.campssg.dto.order.UserOrderListResponseDto;
import com.campssg.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.print.DocFlavor.STRING;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "주문 상태 변경")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "주문 상태 변경 성공")
    })
    @PutMapping("/{orderId}/{status}")
    public ResponseEntity<ResponseMessage> updateOrderStatus(@PathVariable Long orderId, @PathVariable String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "주문 상태 변경 완료"));
    }
}
