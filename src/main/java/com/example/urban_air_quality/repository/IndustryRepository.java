package com.example.urban_air_quality.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.urban_air_quality.model.Industry;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {

    List<Industry> findByIndustryTypeIgnoreCase(String industryType);

    List<Industry> findByLocationIgnoreCase(String location);
}