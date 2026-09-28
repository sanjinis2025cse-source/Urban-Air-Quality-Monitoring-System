package com.example.urban_air_quality.controller;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.urban_air_quality.model.AirQualityReading;
import com.example.urban_air_quality.service.AirQualityReadingService;
import com.example.urban_air_quality.service.OpenMeteoService;

@RestController
@RequestMapping("/api/air-quality")
public class AirQualityReadingController {

    private final AirQualityReadingService service;
    private final OpenMeteoService openMeteoService;

    public AirQualityReadingController(
            AirQualityReadingService service,
            OpenMeteoService openMeteoService) {

        this.service = service;
        this.openMeteoService = openMeteoService;
    }

    @GetMapping
    public List<AirQualityReading> getAllReadings() {
        return service.getAllReadings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirQualityReading> getReadingById(
            @PathVariable Long id) {

        return service.getReadingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/city/{city}")
    public List<AirQualityReading> getReadingsByCity(
            @PathVariable String city) {

        return service.getReadingsByCity(city);
    }

    @GetMapping("/live")
    public ResponseEntity<?> getLiveAirQuality(
            @RequestParam String city) {

        try {
            AirQualityReading reading =
                    openMeteoService.getLiveAirQuality(city);

            return ResponseEntity.ok(reading);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Could not save or read air quality data from the database: "
                            + e.getMostSpecificCause().getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("message", e.getMessage() == null
                            ? "Live air quality lookup failed."
                            : e.getMessage()));
        }
    }

    @PostMapping
    public AirQualityReading createReading(
            @RequestBody AirQualityReading reading) {

        return service.saveReading(reading);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirQualityReading> updateReading(
            @PathVariable Long id,
            @RequestBody AirQualityReading reading) {

        try {
            return ResponseEntity.ok(
                    service.updateReading(id, reading)
            );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReading(
            @PathVariable Long id) {

        try {
            service.deleteReading(id);

            return ResponseEntity.noContent().build();

        } catch (Exception e) {

            return ResponseEntity.notFound().build();
        }
    }
}
