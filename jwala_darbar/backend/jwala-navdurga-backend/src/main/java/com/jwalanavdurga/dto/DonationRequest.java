package com.jwalanavdurga.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class DonationRequest {

    @NotNull(message = "Campaign ID is required")
    private Long campaignId;

    @NotBlank(message = "Donor name is required")
    @Size(
            max = 150,
            message = "Donor name must not exceed 150 characters"
    )
    private String donorName;

    @Size(
            max = 15,
            message = "Mobile number must not exceed 15 characters"
    )
    private String mobileNumber;

    @jakarta.validation.constraints.Email(
            message = "Email must be valid"
    )
    @Size(
            max = 255,
            message = "Email must not exceed 255 characters"
    )
    private String email;

    @NotNull(message = "Donation amount is required")
    @DecimalMin(
            value = "1.00",
            message = "Donation amount must be at least 1.00"
    )
    private BigDecimal amount;

    @NotBlank(message = "Donation type is required")
    private String donationType;

    @Size(
            max = 500,
            message = "Note must not exceed 500 characters"
    )
    private String note;

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}