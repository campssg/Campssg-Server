package com.campssg.demo.DB.repository;

import com.campssg.demo.DB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}