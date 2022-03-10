package com.campssg.DB.repository;

import com.campssg.DB.entity.Mart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MartRepository extends JpaRepository<Mart, Long> {
    List<Mart> findByUser_userId(Long userId);
}
