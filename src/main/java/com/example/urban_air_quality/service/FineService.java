package com.example.urban_air_quality.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.urban_air_quality.model.Fine;
import com.example.urban_air_quality.model.Industry;
import com.example.urban_air_quality.model.Violation;
import com.example.urban_air_quality.repository.FineRepository;
import com.example.urban_air_quality.repository.ViolationRepository;

@Service
public class FineService {

    private final FineRepository repository;
    private final ViolationRepository violationRepository;

    public FineService(
            FineRepository repository,
            ViolationRepository violationRepository) {

        this.repository = repository;
        this.violationRepository = violationRepository;
    }

    public List<Fine> getAllFines() {
        return repository.findAll();
    }

    public Optional<Fine> getFineById(Long id) {
        return repository.findById(id);
    }

    public List<Fine> getFinesByIndustry(Industry industry) {
        return repository.findByIndustry(industry);
    }

    public List<Fine> getFinesByPaymentStatus(String status) {
        return repository.findByPaymentStatusIgnoreCase(status);
    }

    public Fine saveFine(Fine fine) {
        return repository.save(fine);
    }

    public Fine updateFine(Long id, Fine fine) {

        Fine existing = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Fine not found"));

        existing.setViolation(fine.getViolation());
        existing.setIndustry(fine.getIndustry());
        existing.setFineAmount(fine.getFineAmount());
        existing.setIssueDate(fine.getIssueDate());
        existing.setDueDate(fine.getDueDate());
        existing.setPaymentStatus(fine.getPaymentStatus());
        existing.setCreatedAt(fine.getCreatedAt());

        return repository.save(existing);
    }

    public void deleteFine(Long id) {
        repository.deleteById(id);
    }

    public Fine generateFine(Long violationId) {

        Violation violation =
                violationRepository.findById(violationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Violation not found"));

        Optional<Fine> existingFine =
                repository.findByViolation(violation);

        if (existingFine.isPresent()) {
            return existingFine.get();
        }

        double fineAmount =
                calculateFineAmount(
                        violation.getSeverity(),
                        violation.getExceedance()
                );

        LocalDate issueDate =
                LocalDate.now();

        LocalDate dueDate =
                issueDate.plusDays(30);

        Fine fine = new Fine(
                violation,
                violation.getIndustry(),
                fineAmount,
                issueDate,
                dueDate,
                "UNPAID",
                LocalDateTime.now()
        );

        return repository.save(fine);
    }

    private double calculateFineAmount(
            String severity,
            Double exceedance) {

        if (severity == null) {
            return 0.0;
        }

        switch (severity.toUpperCase()) {

            case "LOW":
                return 10000.0;

            case "MEDIUM":
                return 25000.0;

            case "HIGH":
                return 50000.0;

            case "CRITICAL":
                return 100000.0;

            default:
                return 0.0;
        }
    }
}