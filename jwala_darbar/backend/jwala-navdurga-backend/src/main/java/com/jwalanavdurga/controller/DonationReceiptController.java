package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.DonationReceiptResponse;
import com.jwalanavdurga.service.DonationReceiptPdfService;
import com.jwalanavdurga.service.DonationReceiptService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/public/donations")
public class DonationReceiptController {

    private final DonationReceiptService receiptService;
    private final DonationReceiptPdfService receiptPdfService;

    public DonationReceiptController(
            DonationReceiptService receiptService,
            DonationReceiptPdfService receiptPdfService) {

        this.receiptService = receiptService;
        this.receiptPdfService = receiptPdfService;
    }
    
    @PostMapping("/{donationId}/receipt")
    @ResponseStatus(HttpStatus.CREATED)
    public DonationReceiptResponse generateReceipt(
            @PathVariable Long donationId) {

        return receiptService.generateReceipt(donationId);
    }

    @GetMapping("/{donationId}/receipt")
    public DonationReceiptResponse getReceipt(
            @PathVariable Long donationId) {

        return receiptService.getReceiptByDonationId(
                donationId
        );
    }
    
    @GetMapping("/{donationId}/receipt/pdf")
    public ResponseEntity<byte[]> downloadReceiptPdf(
            @PathVariable Long donationId) {

        byte[] pdf =
                receiptPdfService.generateReceiptPdf(
                        donationId
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=donation-receipt-"
                                + donationId
                                + ".pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}