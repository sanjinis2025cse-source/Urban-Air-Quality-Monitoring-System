package com.example.urban_air_quality.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "emission_limits")
public class EmissionLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pollutant;

    @Column(nullable = false)
    private Double permittedValue;

    private String unit;

    public EmissionLimit() {
    }

    public EmissionLimit(
            String pollutant,
            Double permittedValue,
            String unit) {

        this.pollutant = pollutant;
        this.permittedValue = permittedValue;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}