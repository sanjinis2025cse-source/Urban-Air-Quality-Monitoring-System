package com.example.urban_air_quality.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "violations")
public class Violation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;

    @ManyToOne
    @JoinColumn(name = "emission_record_id", nullable = false)
    private EmissionRecord emissionRecord;

    @Column(nullable = false)
    private String pollutant;

    private Double permittedValue;

    private Double actualValue;

    private Double exceedance;

    private String severity;

    private String description;

    private String status;

    private LocalDateTime detectedAt;

    public Violation() {
    }

    public Violation(
            Industry industry,
            EmissionRecord emissionRecord,
            String pollutant,
            Double permittedValue,
            Double actualValue,
            Double exceedance,
            String severity,
            String description,
            String status,
            LocalDateTime detectedAt) {

        this.industry = industry;
        this.emissionRecord = emissionRecord;
        this.pollutant = pollutant;
        this.permittedValue = permittedValue;
        this.actualValue = actualValue;
        this.exceedance = exceedance;
        this.severity = severity;
        this.description = description;
        this.status = status;
        this.detectedAt = detectedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public EmissionRecord getEmissionRecord() {
        return emissionRecord;
    }

    public void setEmissionRecord(EmissionRecord emissionRecord) {
        this.emissionRecord = emissionRecord;
    }

    public String getPollutant() {
        return pollutant;
    }

    public void setPollutant(String pollutant) {
        this.pollutant = pollutant;
    }

    public Double getPermittedValue() {
        return permittedValue;
    }

    public void setPermittedValue(Double permittedValue) {
        this.permittedValue = permittedValue;
    }

    public Double getActualValue() {
        return actualValue;
    }

    public void setActualValue(Double actualValue) {
        this.actualValue = actualValue;
    }

    public Double getExceedance() {
        return exceedance;
    }

    public void setExceedance(Double exceedance) {
        this.exceedance = exceedance;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }
}