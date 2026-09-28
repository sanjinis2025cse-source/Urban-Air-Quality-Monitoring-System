package com.example.urban_air_quality.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "monitoring_stations")
public class MonitoringStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String stationName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String city;

    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "monitoringStation", cascade = CascadeType.ALL)
    private List<AirQualityReading> airQualityReadings = new ArrayList<>();

    public MonitoringStation() {
    }

    public MonitoringStation(
            String stationName,
            String location,
            String city,
            String status) {

        this.stationName = stationName;
        this.location = location;
        this.city = city;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AirQualityReading> getAirQualityReadings() {
        return airQualityReadings;
    }

    public void setAirQualityReadings(
            List<AirQualityReading> airQualityReadings) {

        this.airQualityReadings = airQualityReadings;
    }
}