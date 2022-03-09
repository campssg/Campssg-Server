package com.campssg.DB.repository;

import com.campssg.DB.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, String> {
    Optional<Certification> findByPhoneNumber(String phoneNumber);
}
