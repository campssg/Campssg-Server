package com.campssg.dto.order;

import com.campssg.DB.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderDetailResponseDto {
    private Long orderId;
    private String martName;
    private String userName;
    private String order_phoneNumber;
    private String orderState;
    private String pickup_day;
    private String pickup_time;
    private int cartItemPrice;
    private int charge;
    private int totalPrice;
    private String qrcode_url;
    private List<OrderItemList> orderItemList;
    private List<RequestedProductList> requestedProductList;

    public OrderDetailResponseDto(Order order, List<OrderItemList> orderItemList, List<RequestedProductList> requestedProductList) {
        this.orderId = order.getOrderId();
        this.martName = order.getMart().getMartName();
        this.userName = order.getUser().getUserName();
        this.pickup_day = order.getReservedDate().format(DateTimeFormatter.ofPattern("MM/dd"));
        this.pickup_time = order.getReservedTime();
        this.order_phoneNumber = order.getUser().getPhoneNumber();
        this.orderState = order.getOrderState().toString();
        this.cartItemPrice = order.getTotalPrice() - order.getCharge();
        this.charge = order.getCharge();
        this.totalPrice = order.getTotalPrice();
        this.orderItemList = orderItemList;
        this.qrcode_url = order.getQrcodeUrl();
        if(requestedProductList != null ) {
            this.requestedProductList = requestedProductList;
        }
    }
}
