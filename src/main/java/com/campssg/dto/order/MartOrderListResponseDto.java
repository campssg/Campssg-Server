package com.campssg.dto.order;

import com.campssg.DB.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MartOrderListResponseDto {
    private Long orderId;
    private String userName;
    private String order_phoneNumber;
    private LocalDateTime reservedAt;
    private String orderState;
    private int totalPrice;

    public MartOrderListResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.userName = order.getUser().getUserName();
        this.order_phoneNumber = order.getUser().getPhoneNumber();
        this.reservedAt = order.getReservedAt();
        this.orderState = order.getOrderState().toString();
        this.totalPrice = order.getTotalPrice();
    }
}
