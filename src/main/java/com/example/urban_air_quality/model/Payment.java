package com.example.urban_air_quality.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "fine_id", nullable = false)
    private Fine fine;

    @Column(nullable = false)
    private Double amount;

    private LocalDateTime paymentDate;

    private String paymentMethod;

    private String transactionReference;

    public Payment() {
    }

    public Payment(
            Fine fine,
            Double amount,
            LocalDateTime paymentDate,
            String paymentMethod,
            String transactionReference) {

        this.fine = fine;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.transactionReference = transactionReference;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fine getFine() {
        return fine;
    }

    public void setFine(Fine fine) {
        this.fine = fine;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }
}