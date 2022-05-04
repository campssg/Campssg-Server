package com.campssg.dto.order;

import com.campssg.DB.entity.Order;
import com.campssg.DB.entity.OrderItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private String martName;
    private String pickup_day;
    private String pickup_time;
    private String userName;
    private String order_phoneNumber;
    private String orderState;
    private int cartItemPrice;
    private int charge;
    private Long requestYn;
    private int totalPrice;
    private List<OrderItemList> orderItemList;

    public OrderResponseDto(Order order, List<OrderItemList> orderItemList) {
        this.orderId = order.getOrderId();
        this.martName = order.getMart().getMartName();
        this.pickup_day = order.getReservedAt().format(DateTimeFormatter.ofPattern("MM/dd"));
        this.pickup_time = order.getReservedAt().format(DateTimeFormatter.ofPattern("hh:mm"));
        this.userName = order.getUser().getUserName();
        this.order_phoneNumber = order.getUser().getPhoneNumber();
        this.orderState = order.getOrderState().toString();
        this.cartItemPrice = order.getTotalPrice() - order.getCharge();
        this.charge = order.getCharge();
        this.totalPrice = order.getTotalPrice();
        this.orderItemList = orderItemList;
        this.requestYn = order.getMart().getRequestYn();
    }
}
