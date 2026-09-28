package com.example.urban_air_quality.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.urban_air_quality.model.AirQualityReading;
import com.example.urban_air_quality.repository.AirQualityReadingRepository;

@Service
public class AirQualityReadingService {

    private final AirQualityReadingRepository repository;

    public AirQualityReadingService(AirQualityReadingRepository repository) {
        this.repository = repository;
    }

    public List<AirQualityReading> getAllReadings() {
        return repository.findAll();
    }

    public Optional<AirQualityReading> getReadingById(Long id) {
        return repository.findById(id);
    }

    public List<AirQualityReading> getReadingsByCity(String city) {
        return repository.findByCityIgnoreCase(city);
    }

    public AirQualityReading saveReading(AirQualityReading reading) {
        return repository.save(reading);
    }

    public AirQualityReading updateReading(Long id, AirQualityReading reading) {
        AirQualityReading existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Air quality reading not found"));

        existing.setCity(reading.getCity());
        existing.setLatitude(reading.getLatitude());
        existing.setLongitude(reading.getLongitude());
        existing.setPm25(reading.getPm25());
        existing.setPm10(reading.getPm10());
        existing.setCarbonMonoxide(reading.getCarbonMonoxide());
        existing.setNitrogenDioxide(reading.getNitrogenDioxide());
        existing.setSulphurDioxide(reading.getSulphurDioxide());
        existing.setOzone(reading.getOzone());
        existing.setAqi(reading.getAqi());
        existing.setAirQualityStatus(reading.getAirQualityStatus());
        existing.setRecordedAt(reading.getRecordedAt());
        existing.setMonitoringStation(reading.getMonitoringStation());

        return repository.save(existing);
    }

    public void deleteReading(Long id) {
        repository.deleteById(id);
    }
}