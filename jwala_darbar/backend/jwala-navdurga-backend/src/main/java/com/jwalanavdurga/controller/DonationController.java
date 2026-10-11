package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.DonationRequest;
import com.jwalanavdurga.dto.DonationResponse;
import com.jwalanavdurga.service.DonationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/donations")
public class DonationController {

    private final DonationService donationService;

    public DonationController(
            DonationService donationService) {

        this.donationService = donationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DonationResponse createDonation(
            @Valid @RequestBody DonationRequest request) {

        return donationService.createDonation(request);
    }
}