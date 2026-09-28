package com.example.urban_air_quality.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.urban_air_quality.model.Fine;
import com.example.urban_air_quality.model.Industry;
import com.example.urban_air_quality.model.Violation;

public interface FineRepository extends JpaRepository<Fine, Long> {

    List<Fine> findByIndustry(Industry industry);

    List<Fine> findByPaymentStatusIgnoreCase(String paymentStatus);

    Optional<Fine> findByViolation(Violation violation);
}