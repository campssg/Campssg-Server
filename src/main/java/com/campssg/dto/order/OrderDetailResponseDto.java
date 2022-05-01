package com.campssg.dto.order;

import com.campssg.DB.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderDetailResponseDto {
    private Long orderId;
    private String martName;
    private LocalDateTime reservedAt;
    private String userName;
    private String order_phoneNumber;
    private String orderState;
    private int cartItemPrice;
    private int charge;
    private int totalPrice;
    private List<OrderItemList> orderItemList;
    private List<RequestedProductList> requestedProductList;

    public OrderDetailResponseDto(Order order, List<OrderItemList> orderItemList, List<RequestedProductList> requestedProductList) {
        this.orderId = order.getOrderId();
        this.martName = order.getMart().getMartName();
        this.reservedAt = order.getReservedAt();
        this.userName = order.getUser().getUserName();
        this.order_phoneNumber = order.getUser().getPhoneNumber();
        this.orderState = order.getOrderState().toString();
        this.cartItemPrice = order.getTotalPrice() - order.getCharge();
        this.charge = order.getCharge();
        this.totalPrice = order.getTotalPrice();
        this.orderItemList = orderItemList;
        if(requestedProductList != null ) {
            this.requestedProductList = requestedProductList;
        }
    }
}
