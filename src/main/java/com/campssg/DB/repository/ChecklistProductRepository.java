package com.campssg.DB.repository;

import com.campssg.DB.entity.ChecklistProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistProductRepository extends JpaRepository<ChecklistProduct, Long> {
    List<ChecklistProduct> findByCategory_categoryId(Long categoryId);
}
