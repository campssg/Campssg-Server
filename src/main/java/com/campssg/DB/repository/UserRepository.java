package com.campssg.DB.repository;

import com.campssg.DB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserEmail(String userEmail);
}
