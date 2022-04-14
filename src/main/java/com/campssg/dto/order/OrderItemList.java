package com.campssg.dto.order;

import com.campssg.DB.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemList {
    private Long orderItemId;
    private String orderItemName;
    private int orderItemPrice;
    private int orderItemCount;

    public OrderItemList(OrderItem orderItem) {
        this.orderItemId = orderItem.getOrderItemId();
        this.orderItemName = orderItem.getProduct().getProductName();
        this.orderItemPrice = orderItem.getProduct().getProductPrice();
        this.orderItemCount = orderItem.getOrderItemCount();
    }
}
