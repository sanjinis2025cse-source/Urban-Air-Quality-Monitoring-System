package com.example.urban_air_quality.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.urban_air_quality.model.MonitoringStation;

public interface MonitoringStationRepository extends JpaRepository<MonitoringStation, Long> {

    Optional<MonitoringStation> findFirstByCityIgnoreCase(String city);
}