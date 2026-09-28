package com.example.urban_air_quality.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.urban_air_quality.model.EmissionLimit;

public interface EmissionLimitRepository extends JpaRepository<EmissionLimit, Long> {

    Optional<EmissionLimit> findByPollutantIgnoreCase(String pollutant);
}