package com.campssg.DB.repository;

import com.campssg.DB.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByMart_martId(Long martId);

    Product findByProductId(Long productId);

    @Query(value = "SELECT * FROM product where product_name = :productName and mart_id = :martId", nativeQuery = true)
    Product findByProductNameAndMart_martId(String productName, Long martId);
    List<Product> findByMart_martIdAndCategory_categoryId(Long martId, Long categoryId);
}
