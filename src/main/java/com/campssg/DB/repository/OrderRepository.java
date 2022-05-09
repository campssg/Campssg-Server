package com.campssg.DB.repository;

import com.campssg.DB.entity.Order;
import com.campssg.DB.entity.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderId(Long orderId);
    List<Order> findByUser_userIdOrderByCreatedAtDesc(Long userId);
    List<Order> findByMart_martId(Long martId);
    List<Order> findByUser_userIdAndOrderState(Long userId, OrderState orderState);
    List<Order> findByMart_martIdAndOrderState(Long martId, OrderState orderState);
}
