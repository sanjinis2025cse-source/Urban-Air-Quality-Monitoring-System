package com.example.urban_air_quality.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.urban_air_quality.model.EmissionRecord;
import com.example.urban_air_quality.model.Industry;

@Repository
public interface EmissionRecordRepository extends JpaRepository<EmissionRecord, Long> {

    List<EmissionRecord> findByIndustry(Industry industry);

    List<EmissionRecord> findByComplianceStatusIgnoreCase(String complianceStatus);
}