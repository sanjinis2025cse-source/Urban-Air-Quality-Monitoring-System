package com.example.urban_air_quality.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.urban_air_quality.model.EmissionLimit;
import com.example.urban_air_quality.repository.EmissionLimitRepository;

@Service
public class EmissionLimitService {

    private final EmissionLimitRepository repository;

    public EmissionLimitService(EmissionLimitRepository repository) {
        this.repository = repository;
    }

    public List<EmissionLimit> getAllLimits() {
        return repository.findAll();
    }

    public Optional<EmissionLimit> getLimitById(Long id) {
        return repository.findById(id);
    }

    public Optional<EmissionLimit> getLimitByPollutant(String pollutant) {
        return repository.findByPollutantIgnoreCase(pollutant);
    }

    public EmissionLimit saveLimit(EmissionLimit limit) {
        return repository.save(limit);
    }

    public EmissionLimit updateLimit(Long id, EmissionLimit limit) {

        EmissionLimit existing = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Emission limit not found"));

        existing.setPollutant(limit.getPollutant());
        existing.setPermittedValue(limit.getPermittedValue());
        existing.setUnit(limit.getUnit());

        return repository.save(existing);
    }

    public void deleteLimit(Long id) {
        repository.deleteById(id);
    }
}