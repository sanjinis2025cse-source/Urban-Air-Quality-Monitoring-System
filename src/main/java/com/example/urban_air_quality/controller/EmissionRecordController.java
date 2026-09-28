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

import com.example.urban_air_quality.model.EmissionRecord;
import com.example.urban_air_quality.service.EmissionRecordService;
import com.example.urban_air_quality.service.IndustryService;

@RestController
@RequestMapping("/api/emissions")
public class EmissionRecordController {

    private final EmissionRecordService service;
    private final IndustryService industryService;

    public EmissionRecordController(
            EmissionRecordService service,
            IndustryService industryService) {

        this.service = service;
        this.industryService = industryService;
    }

    @GetMapping
    public ResponseEntity<List<EmissionRecord>> getAllRecords() {
        return ResponseEntity.ok(service.getAllRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmissionRecord> getRecordById(
            @PathVariable Long id) {

        return service.getRecordById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/industry/{industryId}")
    public ResponseEntity<List<EmissionRecord>> getRecordsByIndustry(
            @PathVariable Long industryId) {

        return industryService.getIndustryById(industryId)
                .map(industry ->
                        ResponseEntity.ok(
                                service.getRecordsByIndustry(industry)
                        )
                )
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmissionRecord>> getRecordsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                service.getRecordsByComplianceStatus(status)
        );
    }

    @PostMapping
    public ResponseEntity<EmissionRecord> createRecord(
            @RequestBody EmissionRecord record) {

        return ResponseEntity.ok(
                service.saveRecord(record)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmissionRecord> updateRecord(
            @PathVariable Long id,
            @RequestBody EmissionRecord record) {

        try {
            return ResponseEntity.ok(
                    service.updateRecord(id, record)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(
            @PathVariable Long id) {

        try {
            service.deleteRecord(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}