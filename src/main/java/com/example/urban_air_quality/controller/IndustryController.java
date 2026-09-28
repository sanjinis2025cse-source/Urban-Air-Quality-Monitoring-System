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

import com.example.urban_air_quality.model.Industry;
import com.example.urban_air_quality.service.IndustryService;

@RestController
@RequestMapping("/api/industries")
public class IndustryController {

    private final IndustryService service;

    public IndustryController(IndustryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Industry>> getAllIndustries() {
        return ResponseEntity.ok(service.getAllIndustries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Industry> getIndustryById(
            @PathVariable Long id) {

        return service.getIndustryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{industryType}")
    public ResponseEntity<List<Industry>> getIndustriesByType(
            @PathVariable String industryType) {

        return ResponseEntity.ok(
                service.getIndustriesByType(industryType)
        );
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Industry>> getIndustriesByLocation(
            @PathVariable String location) {

        return ResponseEntity.ok(
                service.getIndustriesByLocation(location)
        );
    }

    @PostMapping
    public ResponseEntity<Industry> createIndustry(
            @RequestBody Industry industry) {

        return ResponseEntity.ok(
                service.saveIndustry(industry)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Industry> updateIndustry(
            @PathVariable Long id,
            @RequestBody Industry industry) {

        try {
            return ResponseEntity.ok(
                    service.updateIndustry(id, industry)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndustry(
            @PathVariable Long id) {

        try {
            service.deleteIndustry(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}