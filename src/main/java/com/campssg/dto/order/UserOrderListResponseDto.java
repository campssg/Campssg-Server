package com.campssg.dto.order;

import com.campssg.DB.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserOrderListResponseDto {
    private Long orderId;
    private String martName;
    private LocalDateTime reservedAt;
    private String orderState;
    private int totalPrice;

    public UserOrderListResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.martName = order.getMart().getMartName();
        this.reservedAt = order.getReservedAt();
        this.orderState = order.getOrderState().toString();
        this.totalPrice = order.getTotalPrice();
    }
}
