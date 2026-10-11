package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.PaymentTransactionRequest;
import com.jwalanavdurga.dto.PaymentTransactionResponse;
import com.jwalanavdurga.dto.PaymentWebhookRequest;
import com.jwalanavdurga.service.PaymentTransactionService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/payments")
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;

    public PaymentTransactionController(
            PaymentTransactionService paymentTransactionService) {

        this.paymentTransactionService =
                paymentTransactionService;
    }

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentTransactionResponse initiatePayment(
            @Valid @RequestBody PaymentTransactionRequest request) {

        return paymentTransactionService.initiatePayment(request);
    }

    @GetMapping("/donation/{donationId}")
    public List<PaymentTransactionResponse> getByDonation(
            @PathVariable Long donationId) {

        return paymentTransactionService
                .getByDonationId(donationId);
    }
    
    @PostMapping("/webhook")
    public PaymentTransactionResponse processWebhook(
            @Valid @RequestBody PaymentWebhookRequest request) {

        return paymentTransactionService.processWebhook(request);
    }
}