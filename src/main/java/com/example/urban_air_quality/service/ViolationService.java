package com.example.urban_air_quality.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.urban_air_quality.model.EmissionLimit;
import com.example.urban_air_quality.model.EmissionRecord;
import com.example.urban_air_quality.model.Industry;
import com.example.urban_air_quality.model.Violation;
import com.example.urban_air_quality.repository.EmissionLimitRepository;
import com.example.urban_air_quality.repository.EmissionRecordRepository;
import com.example.urban_air_quality.repository.ViolationRepository;

@Service
public class ViolationService {

    private final ViolationRepository violationRepository;
    private final EmissionLimitRepository emissionLimitRepository;
    private final EmissionRecordRepository emissionRecordRepository;

    public ViolationService(
            ViolationRepository violationRepository,
            EmissionLimitRepository emissionLimitRepository,
            EmissionRecordRepository emissionRecordRepository) {

        this.violationRepository = violationRepository;
        this.emissionLimitRepository = emissionLimitRepository;
        this.emissionRecordRepository = emissionRecordRepository;
    }

    public List<Violation> getAllViolations() {
        return violationRepository.findAll();
    }

    public Optional<Violation> getViolationById(Long id) {
        return violationRepository.findById(id);
    }

    public List<Violation> getViolationsByIndustry(Industry industry) {
        return violationRepository.findByIndustry(industry);
    }

    public List<Violation> getViolationsBySeverity(String severity) {
        return violationRepository.findBySeverityIgnoreCase(severity);
    }

    public List<Violation> getViolationsByStatus(String status) {
        return violationRepository.findByStatusIgnoreCase(status);
    }

    public Violation saveViolation(Violation violation) {
        return violationRepository.save(violation);
    }

    public Violation updateViolation(
            Long id,
            Violation violation) {

        Violation existing = violationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Violation not found"));

        existing.setIndustry(violation.getIndustry());
        existing.setEmissionRecord(violation.getEmissionRecord());
        existing.setPollutant(violation.getPollutant());
        existing.setPermittedValue(violation.getPermittedValue());
        existing.setActualValue(violation.getActualValue());
        existing.setExceedance(violation.getExceedance());
        existing.setSeverity(violation.getSeverity());
        existing.setDescription(violation.getDescription());
        existing.setStatus(violation.getStatus());
        existing.setDetectedAt(violation.getDetectedAt());

        return violationRepository.save(existing);
    }

    public void deleteViolation(Long id) {
        violationRepository.deleteById(id);
    }

    public List<Violation> detectViolations(Long emissionRecordId) {

        EmissionRecord record =
                emissionRecordRepository.findById(emissionRecordId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Emission record not found"));

        List<Violation> violations = new ArrayList<>();

        checkPollutant(
                record,
                "PM2.5",
                record.getPm25(),
                violations
        );

        checkPollutant(
                record,
                "PM10",
                record.getPm10(),
                violations
        );

        checkPollutant(
                record,
                "CO",
                record.getCarbonMonoxide(),
                violations
        );

        checkPollutant(
                record,
                "NO2",
                record.getNitrogenDioxide(),
                violations
        );

        checkPollutant(
                record,
                "SO2",
                record.getSulphurDioxide(),
                violations
        );

        checkPollutant(
                record,
                "O3",
                record.getOzone(),
                violations
        );

        return violations;
    }

    private void checkPollutant(
            EmissionRecord record,
            String pollutant,
            Double actualValue,
            List<Violation> violations) {

        if (actualValue == null) {
            return;
        }

        Optional<EmissionLimit> limitOptional =
                emissionLimitRepository
                        .findByPollutantIgnoreCase(pollutant);

        if (limitOptional.isEmpty()) {
            return;
        }

        EmissionLimit limit = limitOptional.get();

        Double permittedValue = limit.getPermittedValue();

        if (actualValue > permittedValue) {

            Optional<Violation> existingViolation =
                    violationRepository.findByEmissionRecordAndPollutant(
                            record,
                            pollutant
                    );

            if (existingViolation.isPresent()) {
                violations.add(existingViolation.get());
                return;
            }

            double exceedance =
                    actualValue - permittedValue;

            String severity =
                    calculateSeverity(
                            actualValue,
                            permittedValue
                    );

            Violation violation = new Violation(
                    record.getIndustry(),
                    record,
                    pollutant,
                    permittedValue,
                    actualValue,
                    exceedance,
                    severity,
                    pollutant
                            + " emission exceeded the configured permitted limit.",
                    "OPEN",
                    LocalDateTime.now()
            );

            Violation savedViolation =
                    violationRepository.save(violation);

            violations.add(savedViolation);
        }
    }

    private String calculateSeverity(
            double actualValue,
            double permittedValue) {

        double percentage =
                ((actualValue - permittedValue)
                        / permittedValue) * 100;

        if (percentage <= 10) {
            return "LOW";
        } else if (percentage <= 25) {
            return "MEDIUM";
        } else if (percentage <= 50) {
            return "HIGH";
        } else {
            return "CRITICAL";
        }
    }
}