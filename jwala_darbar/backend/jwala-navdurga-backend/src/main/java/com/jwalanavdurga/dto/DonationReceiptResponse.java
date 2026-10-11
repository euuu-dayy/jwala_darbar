package com.jwalanavdurga.dto;

import java.time.LocalDateTime;

public class DonationReceiptResponse {

    private Long id;
    private Long donationId;
    private String receiptNumber;
    private LocalDateTime receiptDate;
    private String receiptDocumentUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDonationId() {
        return donationId;
    }

    public void setDonationId(Long donationId) {
        this.donationId = donationId;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public LocalDateTime getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDateTime receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceiptDocumentUrl() {
        return receiptDocumentUrl;
    }

    public void setReceiptDocumentUrl(String receiptDocumentUrl) {
        this.receiptDocumentUrl = receiptDocumentUrl;
    }
}