package com.example.urban_air_quality.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.urban_air_quality.model.Fine;
import com.example.urban_air_quality.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByFine(Fine fine);
}