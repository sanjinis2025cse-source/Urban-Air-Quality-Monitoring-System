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
@Table(name = "air_quality_readings")
public class AirQualityReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private MonitoringStation monitoringStation;

    @Column(nullable = false)
    private String city;

    private Double latitude;

    private Double longitude;

    private Double pm25;

    private Double pm10;

    private Double carbonMonoxide;

    private Double nitrogenDioxide;

    private Double sulphurDioxide;

    private Double ozone;

    private Double aqi;

    private String airQualityStatus;

    private LocalDateTime recordedAt;

    public AirQualityReading() {
    }

    public AirQualityReading(
            String city,
            Double latitude,
            Double longitude,
            Double pm25,
            Double pm10,
            Double carbonMonoxide,
            Double nitrogenDioxide,
            Double sulphurDioxide,
            Double ozone,
            Double aqi,
            String airQualityStatus,
            LocalDateTime recordedAt) {

        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.carbonMonoxide = carbonMonoxide;
        this.nitrogenDioxide = nitrogenDioxide;
        this.sulphurDioxide = sulphurDioxide;
        this.ozone = ozone;
        this.aqi = aqi;
        this.airQualityStatus = airQualityStatus;
        this.recordedAt = recordedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonitoringStation getMonitoringStation() {
        return monitoringStation;
    }

    public void setMonitoringStation(MonitoringStation monitoringStation) {
        this.monitoringStation = monitoringStation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public Double getAqi() {
        return aqi;
    }

    public void setAqi(Double aqi) {
        this.aqi = aqi;
    }

    public String getAirQualityStatus() {
        return airQualityStatus;
    }

    public void setAirQualityStatus(String airQualityStatus) {
        this.airQualityStatus = airQualityStatus;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}