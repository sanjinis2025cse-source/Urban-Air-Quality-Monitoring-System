package com.example.urban_air_quality.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.urban_air_quality.model.EmissionRecord;
import com.example.urban_air_quality.model.Industry;
import com.example.urban_air_quality.model.Violation;

public interface ViolationRepository extends JpaRepository<Violation, Long> {

    List<Violation> findByIndustry(Industry industry);

    List<Violation> findBySeverityIgnoreCase(String severity);

    List<Violation> findByStatusIgnoreCase(String status);

    Optional<Violation> findByEmissionRecordAndPollutant(
            EmissionRecord emissionRecord,
            String pollutant
    );
}