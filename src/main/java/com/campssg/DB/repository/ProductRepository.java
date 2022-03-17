package com.campssg.DB.repository;

import com.campssg.DB.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByMart_martId(Long martId);
    Product findByProductId(Long productId);
}
