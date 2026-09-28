package com.example.urban_air_quality.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.urban_air_quality.model.Industry;
import com.example.urban_air_quality.repository.IndustryRepository;

@Service
public class IndustryService {

    private final IndustryRepository repository;

    public IndustryService(IndustryRepository repository) {
        this.repository = repository;
    }

    public List<Industry> getAllIndustries() {
        return repository.findAll();
    }

    public Optional<Industry> getIndustryById(Long id) {
        return repository.findById(id);
    }

    public List<Industry> getIndustriesByType(String industryType) {
        return repository.findByIndustryTypeIgnoreCase(industryType);
    }

    public List<Industry> getIndustriesByLocation(String location) {
        return repository.findByLocationIgnoreCase(location);
    }

    public Industry saveIndustry(Industry industry) {
        return repository.save(industry);
    }

    public Industry updateIndustry(Long id, Industry industry) {
        Industry existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Industry not found"));

        existing.setIndustryName(industry.getIndustryName());
        existing.setIndustryType(industry.getIndustryType());
        existing.setLocation(industry.getLocation());
        existing.setContactPerson(industry.getContactPerson());
        existing.setContactNumber(industry.getContactNumber());
        existing.setStatus(industry.getStatus());

        return repository.save(existing);
    }

    public void deleteIndustry(Long id) {
        repository.deleteById(id);
    }
}