package com.campssg.DB.repository;

import com.campssg.DB.entity.CampWishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampWishRepository extends JpaRepository<CampWishlist, Long> {
    List<CampWishlist> findByUser_userId(Long userId);
}
