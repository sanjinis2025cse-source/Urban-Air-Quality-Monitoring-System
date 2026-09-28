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

import com.example.urban_air_quality.model.MonitoringStation;
import com.example.urban_air_quality.service.MonitoringStationService;

@RestController
@RequestMapping("/api/stations")
public class MonitoringStationController {

    private final MonitoringStationService service;

    public MonitoringStationController(MonitoringStationService service) {
        this.service = service;
    }

    @GetMapping
    public List<MonitoringStation> getAllStations() {
        return service.getAllStations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonitoringStation> getStationById(@PathVariable Long id) {
        return service.getStationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MonitoringStation createStation(@RequestBody MonitoringStation station) {
        return service.saveStation(station);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonitoringStation> updateStation(
            @PathVariable Long id,
            @RequestBody MonitoringStation station) {

        try {
            return ResponseEntity.ok(service.updateStation(id, station));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id) {
        try {
            service.deleteStation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}