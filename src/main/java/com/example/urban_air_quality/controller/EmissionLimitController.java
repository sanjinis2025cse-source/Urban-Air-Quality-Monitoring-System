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

import com.example.urban_air_quality.model.EmissionLimit;
import com.example.urban_air_quality.service.EmissionLimitService;

@RestController
@RequestMapping("/api/emission-limits")
public class EmissionLimitController {

    private final EmissionLimitService service;

    public EmissionLimitController(EmissionLimitService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EmissionLimit>> getAllLimits() {
        return ResponseEntity.ok(service.getAllLimits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmissionLimit> getLimitById(
            @PathVariable Long id) {

        return service.getLimitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pollutant/{pollutant}")
    public ResponseEntity<EmissionLimit> getLimitByPollutant(
            @PathVariable String pollutant) {

        return service.getLimitByPollutant(pollutant)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmissionLimit> createLimit(
            @RequestBody EmissionLimit limit) {

        return ResponseEntity.ok(
                service.saveLimit(limit)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmissionLimit> updateLimit(
            @PathVariable Long id,
            @RequestBody EmissionLimit limit) {

        try {
            return ResponseEntity.ok(
                    service.updateLimit(id, limit)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLimit(
            @PathVariable Long id) {

        try {
            service.deleteLimit(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}