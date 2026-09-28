package com.example.urban_air_quality.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.urban_air_quality.model.Fine;
import com.example.urban_air_quality.service.FineService;
import com.example.urban_air_quality.service.IndustryService;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    private final FineService service;
    private final IndustryService industryService;

    public FineController(
            FineService service,
            IndustryService industryService) {

        this.service = service;
        this.industryService = industryService;
    }

    @GetMapping
    public ResponseEntity<List<Fine>> getAllFines() {
        return ResponseEntity.ok(
                service.getAllFines()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fine> getFineById(
            @PathVariable Long id) {

        return service.getFineById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/industry/{industryId}")
    public ResponseEntity<List<Fine>> getFinesByIndustry(
            @PathVariable Long industryId) {

        return industryService.getIndustryById(industryId)
                .map(industry ->
                        ResponseEntity.ok(
                                service.getFinesByIndustry(industry)
                        )
                )
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/payment-status/{status}")
    public ResponseEntity<List<Fine>> getFinesByPaymentStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                service.getFinesByPaymentStatus(status)
        );
    }

    @PostMapping
    public ResponseEntity<Fine> createFine(
            @RequestBody Fine fine) {

        return ResponseEntity.ok(
                service.saveFine(fine)
        );
    }

    @PostMapping("/generate/{violationId}")
    public ResponseEntity<Fine> generateFine(
            @PathVariable Long violationId) {

        try {
            return ResponseEntity.ok(
                    service.generateFine(violationId)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fine> updateFine(
            @PathVariable Long id,
            @RequestBody Fine fine) {

        try {
            return ResponseEntity.ok(
                    service.updateFine(id, fine)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFine(
            @PathVariable Long id) {

        try {
            service.deleteFine(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}