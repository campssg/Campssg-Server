package com.campssg.dto.order;

import com.campssg.DB.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class UserOrderListResponseDto {
    private Long orderId;
    private String martName;
    private String pickup_day;
    private String pickup_time;
    private String orderState;
    private int totalPrice;

    public UserOrderListResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.martName = order.getMart().getMartName();
        this.pickup_day = order.getReservedDate().format(DateTimeFormatter.ofPattern("MM/dd"));
        this.pickup_time = order.getReservedTime();
        this.orderState = order.getOrderState().toString();
        this.totalPrice = order.getTotalPrice();
    }
}
