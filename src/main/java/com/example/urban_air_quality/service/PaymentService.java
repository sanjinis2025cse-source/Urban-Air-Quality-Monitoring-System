package com.example.urban_air_quality.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.urban_air_quality.model.Fine;
import com.example.urban_air_quality.model.Payment;
import com.example.urban_air_quality.repository.FineRepository;
import com.example.urban_air_quality.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final FineRepository fineRepository;

    public PaymentService(
            PaymentRepository repository,
            FineRepository fineRepository) {

        this.repository = repository;
        this.fineRepository = fineRepository;
    }

    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return repository.findById(id);
    }

    public Optional<Payment> getPaymentByFine(Fine fine) {
        return repository.findByFine(fine);
    }

    public Payment savePayment(Payment payment) {
        return repository.save(payment);
    }

    public Payment processPayment(
            Long fineId,
            String paymentMethod) {

        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() ->
                        new RuntimeException("Fine not found"));

        if ("PAID".equalsIgnoreCase(fine.getPaymentStatus())) {
            throw new RuntimeException(
                    "Fine has already been paid");
        }

        Optional<Payment> existingPayment =
                repository.findByFine(fine);

        if (existingPayment.isPresent()) {
            throw new RuntimeException(
                    "Payment already exists for this fine");
        }

        String transactionReference =
                "TXN-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        Payment payment = new Payment(
                fine,
                fine.getFineAmount(),
                LocalDateTime.now(),
                paymentMethod,
                transactionReference
        );

        Payment savedPayment = repository.save(payment);

        fine.setPaymentStatus("PAID");
        fineRepository.save(fine);

        return savedPayment;
    }

    public Payment updatePayment(
            Long id,
            Payment payment) {

        Payment existing = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found"));

        existing.setFine(payment.getFine());
        existing.setAmount(payment.getAmount());
        existing.setPaymentDate(payment.getPaymentDate());
        existing.setPaymentMethod(payment.getPaymentMethod());
        existing.setTransactionReference(
                payment.getTransactionReference()
        );

        return repository.save(existing);
    }

    public void deletePayment(Long id) {
        repository.deleteById(id);
    }
}