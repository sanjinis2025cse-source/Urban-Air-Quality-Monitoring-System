package com.example.urban_air_quality.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.urban_air_quality.model.EmissionRecord;
import com.example.urban_air_quality.model.Industry;
import com.example.urban_air_quality.repository.EmissionRecordRepository;

@Service
public class EmissionRecordService {

    private final EmissionRecordRepository repository;

    public EmissionRecordService(EmissionRecordRepository repository) {
        this.repository = repository;
    }

    public List<EmissionRecord> getAllRecords() {
        return repository.findAll();
    }

    public Optional<EmissionRecord> getRecordById(Long id) {
        return repository.findById(id);
    }

    public List<EmissionRecord> getRecordsByIndustry(Industry industry) {
        return repository.findByIndustry(industry);
    }

    public List<EmissionRecord> getRecordsByComplianceStatus(String status) {
        return repository.findByComplianceStatusIgnoreCase(status);
    }

    public EmissionRecord saveRecord(EmissionRecord record) {
        return repository.save(record);
    }

    public EmissionRecord updateRecord(Long id, EmissionRecord record) {
        EmissionRecord existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emission record not found"));

        existing.setIndustry(record.getIndustry());
        existing.setPm25(record.getPm25());
        existing.setPm10(record.getPm10());
        existing.setCarbonMonoxide(record.getCarbonMonoxide());
        existing.setNitrogenDioxide(record.getNitrogenDioxide());
        existing.setSulphurDioxide(record.getSulphurDioxide());
        existing.setOzone(record.getOzone());
        existing.setComplianceStatus(record.getComplianceStatus());
        existing.setRecordedAt(record.getRecordedAt());

        return repository.save(existing);
    }

    public void deleteRecord(Long id) {
        repository.deleteById(id);
    }
}