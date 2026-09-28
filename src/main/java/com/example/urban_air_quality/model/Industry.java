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
@Table(name = "industries")
public class Industry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String industryName;

    @Column(nullable = false)
    private String industryType;

    @Column(nullable = false)
    private String location;

    private String contactPerson;
    private String contactNumber;
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "industry", cascade = CascadeType.ALL)
    private List<EmissionRecord> emissionRecords = new ArrayList<>();

    public Industry() {
    }

    public Industry(
            String industryName,
            String industryType,
            String location,
            String contactPerson,
            String contactNumber,
            String status) {

        this.industryName = industryName;
        this.industryType = industryType;
        this.location = location;
        this.contactPerson = contactPerson;
        this.contactNumber = contactNumber;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EmissionRecord> getEmissionRecords() {
        return emissionRecords;
    }

    public void setEmissionRecords(
            List<EmissionRecord> emissionRecords) {

        this.emissionRecords = emissionRecords;
    }
}