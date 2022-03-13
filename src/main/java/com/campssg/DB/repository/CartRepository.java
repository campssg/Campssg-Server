package com.campssg.DB.repository;

import com.campssg.DB.entity.Cart;
import com.campssg.DB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser_userId(Long userId);
}
