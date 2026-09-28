package com.example.urban_air_quality.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.urban_air_quality.model.AirQualityReading;

@Repository
public interface AirQualityReadingRepository extends JpaRepository<AirQualityReading, Long> {

    List<AirQualityReading> findByCityIgnoreCase(String city);
}