package com.example.urban_air_quality.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.urban_air_quality.model.MonitoringStation;
import com.example.urban_air_quality.repository.MonitoringStationRepository;

@Service
public class MonitoringStationService {

    private final MonitoringStationRepository repository;

    public MonitoringStationService(MonitoringStationRepository repository) {
        this.repository = repository;
    }

    public List<MonitoringStation> getAllStations() {
        return repository.findAll();
    }

    public Optional<MonitoringStation> getStationById(Long id) {
        return repository.findById(id);
    }

    public MonitoringStation saveStation(MonitoringStation station) {
        return repository.save(station);
    }

    public MonitoringStation updateStation(Long id, MonitoringStation station) {
        MonitoringStation existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Monitoring station not found"));

        existing.setStationName(station.getStationName());
        existing.setLocation(station.getLocation());
        existing.setCity(station.getCity());
        existing.setStatus(station.getStatus());

        return repository.save(existing);
    }

    public void deleteStation(Long id) {
        repository.deleteById(id);
    }
}