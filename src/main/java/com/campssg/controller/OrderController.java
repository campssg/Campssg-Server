package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.order.MartOrderListResponseDto;
import com.campssg.dto.order.OrderDetailResponseDto;
import com.campssg.dto.order.OrderRequestDto;
import com.campssg.dto.order.OrderResponseDto;
import com.campssg.dto.order.UserOrderListResponseDto;
import com.campssg.service.OrderService;
import com.google.zxing.WriterException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public OrderResponseDto addOrderInfo(@Valid @RequestBody OrderRequestDto orderRequestDto)
        throws IOException, WriterException {
        return orderService.addOrderInfo(orderRequestDto);
    }

    @ApiOperation(value = "주문 상세 내역 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "주문 상세 내역 조회 성공")
    })
    @GetMapping("/{orderId}")
    public OrderDetailResponseDto getOrderInfo(@PathVariable Long orderId) {
        return orderService.getOrderInfo(orderId);
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

    @ApiOperation(value = "주문 상태 변경")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "주문 상태 변경 성공")
    })
    @PutMapping("/{orderId}/{status}")
    public ResponseEntity<ResponseMessage> updateOrderStatus(@ApiParam(value = "주문 아이디") @PathVariable Long orderId,
        @ApiParam(value = "주문 상태(주문완료, 결제대기중, 가격흥정중, 결제완료, 픽업준비중, 픽업준비완료, 픽업완료)") @PathVariable String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "주문 상태 변경 완료"));
    }

    @ApiOperation(value = "주문 상태에 따른 주문 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "주문 목록 조회 성공")
    })
    @GetMapping("/get/{orderState}")
    public ResponseEntity<List<UserOrderListResponseDto>> getOrderListByState(@PathVariable String orderState) {
        return ResponseEntity.ok(orderService.getOrderListByState(orderState));
    }
}
