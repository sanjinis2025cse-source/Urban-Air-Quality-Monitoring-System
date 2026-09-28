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

import com.example.urban_air_quality.model.Violation;
import com.example.urban_air_quality.service.IndustryService;
import com.example.urban_air_quality.service.ViolationService;

@RestController
@RequestMapping("/api/violations")
public class ViolationController {

    private final ViolationService service;
    private final IndustryService industryService;

    public ViolationController(
            ViolationService service,
            IndustryService industryService) {

        this.service = service;
        this.industryService = industryService;
    }

    @GetMapping
    public ResponseEntity<List<Violation>> getAllViolations() {
        return ResponseEntity.ok(
                service.getAllViolations()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Violation> getViolationById(
            @PathVariable Long id) {

        return service.getViolationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/industry/{industryId}")
    public ResponseEntity<List<Violation>> getViolationsByIndustry(
            @PathVariable Long industryId) {

        return industryService.getIndustryById(industryId)
                .map(industry ->
                        ResponseEntity.ok(
                                service.getViolationsByIndustry(industry)
                        )
                )
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<Violation>> getViolationsBySeverity(
            @PathVariable String severity) {

        return ResponseEntity.ok(
                service.getViolationsBySeverity(severity)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Violation>> getViolationsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                service.getViolationsByStatus(status)
        );
    }

    @PostMapping
    public ResponseEntity<Violation> createViolation(
            @RequestBody Violation violation) {

        return ResponseEntity.ok(
                service.saveViolation(violation)
        );
    }

    @PostMapping("/detect/{emissionRecordId}")
    public ResponseEntity<List<Violation>> detectViolations(
            @PathVariable Long emissionRecordId) {

        try {
            return ResponseEntity.ok(
                    service.detectViolations(emissionRecordId)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Violation> updateViolation(
            @PathVariable Long id,
            @RequestBody Violation violation) {

        try {
            return ResponseEntity.ok(
                    service.updateViolation(id, violation)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViolation(
            @PathVariable Long id) {

        try {
            service.deleteViolation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}