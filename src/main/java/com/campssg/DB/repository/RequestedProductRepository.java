package com.campssg.DB.repository;

import com.campssg.DB.entity.RequestedProduct;
import com.campssg.DB.entity.RequestedProductState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestedProductRepository extends JpaRepository<RequestedProduct, Long> {
    List<RequestedProduct> findByOrder_orderId(Long orderId);
    List<RequestedProduct> findByMart_martIdAndRequestedProductState(Long martId, RequestedProductState requestedProductState);
    List<RequestedProduct> findByUser_userIdAndRequestedProductState(Long userId, RequestedProductState requestedProductState);
    RequestedProduct findByRequestedProductId(Long requestedProductId);
}
