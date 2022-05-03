package com.campssg.DB.repository;

import com.campssg.DB.entity.MartWishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MartWishRepository extends JpaRepository<MartWishlist, Long> {
    List<MartWishlist> findByUser_userId(Long userId);
}
