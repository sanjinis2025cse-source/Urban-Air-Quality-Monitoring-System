package com.example.urban_air_quality.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "emission_records")
public class EmissionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;

    private Double pm25;

    private Double pm10;

    private Double carbonMonoxide;

    private Double nitrogenDioxide;

    private Double sulphurDioxide;

    private Double ozone;

    private String complianceStatus;

    private LocalDateTime recordedAt;

    public EmissionRecord() {
    }

    public EmissionRecord(
            Industry industry,
            Double pm25,
            Double pm10,
            Double carbonMonoxide,
            Double nitrogenDioxide,
            Double sulphurDioxide,
            Double ozone,
            String complianceStatus,
            LocalDateTime recordedAt) {

        this.industry = industry;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.carbonMonoxide = carbonMonoxide;
        this.nitrogenDioxide = nitrogenDioxide;
        this.sulphurDioxide = sulphurDioxide;
        this.ozone = ozone;
        this.complianceStatus = complianceStatus;
        this.recordedAt = recordedAt;
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

    public Double getPm25() {
        return pm25;
    }

    public void setPm25(Double pm25) {
        this.pm25 = pm25;
    }

    public Double getPm10() {
        return pm10;
    }

    public void setPm10(Double pm10) {
        this.pm10 = pm10;
    }

    public Double getCarbonMonoxide() {
        return carbonMonoxide;
    }

    public void setCarbonMonoxide(Double carbonMonoxide) {
        this.carbonMonoxide = carbonMonoxide;
    }

    public Double getNitrogenDioxide() {
        return nitrogenDioxide;
    }

    public void setNitrogenDioxide(Double nitrogenDioxide) {
        this.nitrogenDioxide = nitrogenDioxide;
    }

    public Double getSulphurDioxide() {
        return sulphurDioxide;
    }

    public void setSulphurDioxide(Double sulphurDioxide) {
        this.sulphurDioxide = sulphurDioxide;
    }

    public Double getOzone() {
        return ozone;
    }

    public void setOzone(Double ozone) {
        this.ozone = ozone;
    }

    public String getComplianceStatus() {
        return complianceStatus;
    }

    public void setComplianceStatus(String complianceStatus) {
        this.complianceStatus = complianceStatus;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}