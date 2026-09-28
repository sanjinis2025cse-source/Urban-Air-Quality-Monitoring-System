package com.example.urban_air_quality.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.urban_air_quality.model.Payment;
import com.example.urban_air_quality.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(
                service.getAllPayments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(
            @PathVariable Long id) {

        return service.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @RequestBody Payment payment) {

        return ResponseEntity.ok(
                service.savePayment(payment)
        );
    }

    @PostMapping("/process/{fineId}")
    public ResponseEntity<Payment> processPayment(
            @PathVariable Long fineId,
            @RequestParam String paymentMethod) {

        try {
            return ResponseEntity.ok(
                    service.processPayment(
                            fineId,
                            paymentMethod
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable Long id,
            @RequestBody Payment payment) {

        try {
            return ResponseEntity.ok(
                    service.updatePayment(id, payment)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(
            @PathVariable Long id) {

        try {
            service.deletePayment(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}