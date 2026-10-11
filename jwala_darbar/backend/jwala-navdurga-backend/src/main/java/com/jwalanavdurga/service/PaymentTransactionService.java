package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.PaymentTransactionRequest;
import com.jwalanavdurga.dto.PaymentTransactionResponse;
import com.jwalanavdurga.dto.PaymentWebhookRequest;
import com.jwalanavdurga.entity.Donation;
import com.jwalanavdurga.entity.PaymentTransaction;
import com.jwalanavdurga.exception.BusinessException;
import com.jwalanavdurga.exception.ResourceNotFoundException;
import com.jwalanavdurga.repository.DonationRepository;
import com.jwalanavdurga.repository.PaymentTransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentTransactionService {

    private static final Logger log =
            LoggerFactory.getLogger(PaymentTransactionService.class);

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final DonationRepository donationRepository;

    public PaymentTransactionService(
            PaymentTransactionRepository paymentTransactionRepository,
            DonationRepository donationRepository) {

        this.paymentTransactionRepository = paymentTransactionRepository;
        this.donationRepository = donationRepository;
    }

    @Transactional
    public PaymentTransactionResponse initiatePayment(
            PaymentTransactionRequest request) {

        log.info(
                "Initiating payment transaction | donationId={} | gateway={}",
                request.getDonationId(),
                request.getGateway()
        );

        Donation donation = donationRepository
                .findById(request.getDonationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Donation not found.")
                );

        if (!"PENDING".equalsIgnoreCase(donation.getStatus())) {
            throw new BusinessException(
                    "Payment cannot be initiated for this donation."
            );
        }

        if (request.getGateway().trim().isEmpty()) {
            throw new BusinessException("Payment gateway is required.");
        }

        PaymentTransaction transaction = new PaymentTransaction();

        transaction.setDonationId(donation.getId());

        transaction.setGateway(
                request.getGateway().trim().toUpperCase()
        );

        transaction.setPaymentMethod(
                request.getPaymentMethod()
        );

        transaction.setAmount(donation.getAmount());

        transaction.setCurrency("INR");

        transaction.setStatus("INITIATED");

        LocalDateTime now = LocalDateTime.now();

        transaction.setInitiatedAt(now);
        transaction.setCreatedAt(now);
        transaction.setUpdatedAt(now);

        PaymentTransaction saved =
                paymentTransactionRepository.save(transaction);

        log.info(
                "Payment transaction initiated | transactionId={} | donationId={}",
                saved.getId(),
                saved.getDonationId()
        );

        return mapToResponse(saved);
    }

    public List<PaymentTransactionResponse> getByDonationId(
            Long donationId) {

        if (!donationRepository.existsById(donationId)) {
            throw new ResourceNotFoundException(
                    "Donation not found."
            );
        }

        return paymentTransactionRepository
                .findByDonationIdOrderByInitiatedAtDesc(donationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PaymentTransactionResponse mapToResponse(
            PaymentTransaction transaction) {

        PaymentTransactionResponse response =
                new PaymentTransactionResponse();

        response.setId(transaction.getId());
        response.setDonationId(transaction.getDonationId());
        response.setGateway(transaction.getGateway());
        response.setGatewayTransactionId(
                transaction.getGatewayTransactionId()
        );
        response.setPaymentMethod(transaction.getPaymentMethod());
        response.setAmount(transaction.getAmount());
        response.setCurrency(transaction.getCurrency());
        response.setStatus(transaction.getStatus());
        response.setInitiatedAt(transaction.getInitiatedAt());
        response.setCompletedAt(transaction.getCompletedAt());

        return response;
    }
    
    @Transactional
    public PaymentTransactionResponse processWebhook(
            PaymentWebhookRequest request) {

        log.info(
                "Processing payment webhook | gateway={} | transactionId={}",
                request.getGateway(),
                request.getGatewayTransactionId()
        );

        PaymentTransaction transaction =
                paymentTransactionRepository
                        .findByGatewayAndGatewayTransactionId(
                                request.getGateway().trim().toUpperCase(),
                                request.getGatewayTransactionId().trim()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment transaction not found."
                                )
                        );

        String newStatus =
                request.getStatus().trim().toUpperCase();

        if (!isValidPaymentStatus(newStatus)) {
            throw new BusinessException(
                    "Invalid payment status."
            );
        }

        /*
         * Idempotency:
         * If the same webhook is received again with the
         * same terminal status, simply return the existing
         * transaction.
         */
        if (isTerminalStatus(transaction.getStatus())) {

            if (transaction.getStatus().equals(newStatus)) {

                log.info(
                        "Duplicate webhook ignored | transactionId={} | status={}",
                        transaction.getId(),
                        newStatus
                );

                return mapToResponse(transaction);
            }

            throw new BusinessException(
                    "Payment transaction is already completed."
            );
        }

        transaction.setStatus(newStatus);

        if (request.getPaymentMethod() != null
                && !request.getPaymentMethod().isBlank()) {

            transaction.setPaymentMethod(
                    request.getPaymentMethod().trim()
            );
        }

        LocalDateTime now = LocalDateTime.now();

        if ("SUCCESS".equals(newStatus)
                || "FAILED".equals(newStatus)
                || "CANCELLED".equals(newStatus)) {

            transaction.setCompletedAt(now);
        }

        transaction.setUpdatedAt(now);

        PaymentTransaction saved =
                paymentTransactionRepository.save(transaction);

        /*
         * Update corresponding donation.
         */
        Donation donation =
                donationRepository
                        .findById(transaction.getDonationId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Donation not found."
                                )
                        );

        donation.setStatus(newStatus);
        donation.setDonationStatus(newStatus);

        if ("SUCCESS".equals(newStatus)) {
            donation.setDonatedAt(now);
        }

        donationRepository.save(donation);

        log.info(
                "Payment webhook processed | transactionId={} | status={}",
                saved.getId(),
                newStatus
        );

        return mapToResponse(saved);
    }

    private boolean isValidPaymentStatus(String status) {

        return "SUCCESS".equals(status)
                || "FAILED".equals(status)
                || "CANCELLED".equals(status);
    }

    private boolean isTerminalStatus(String status) {

        return "SUCCESS".equalsIgnoreCase(status)
                || "FAILED".equalsIgnoreCase(status)
                || "CANCELLED".equalsIgnoreCase(status);
    }
}