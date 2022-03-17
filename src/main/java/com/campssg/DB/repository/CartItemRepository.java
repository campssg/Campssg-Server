package com.campssg.DB.repository;

import com.campssg.DB.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCart_cartIdAndProduct_productId(Long cartId, Long productId);
    List<CartItem> findByCart_cartId(Long cartId);
}
