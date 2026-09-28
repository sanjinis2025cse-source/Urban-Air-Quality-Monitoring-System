package com.example.urban_air_quality.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "fines")
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "violation_id", nullable = false)
    private Violation violation;

    @ManyToOne
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;

    @Column(nullable = false)
    private Double fineAmount;

    private LocalDate issueDate;

    private LocalDate dueDate;

    private String paymentStatus;

    private LocalDateTime createdAt;

    public Fine() {
    }

    public Fine(
            Violation violation,
            Industry industry,
            Double fineAmount,
            LocalDate issueDate,
            LocalDate dueDate,
            String paymentStatus,
            LocalDateTime createdAt) {

        this.violation = violation;
        this.industry = industry;
        this.fineAmount = fineAmount;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public Double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}