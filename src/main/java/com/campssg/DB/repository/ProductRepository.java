package com.campssg.DB.repository;

import com.campssg.DB.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByMart_martId(Long martId);

    Product findByProductId(Long productId);

    @Query(value = "SELECT * FROM product where product_id = :productId and mart_id = :martId and product_stock = '0'", nativeQuery = true)
    List<Product> findByProductIdAndMart_martIdAndProductStockIs0(Long productId, Long martId);
}
