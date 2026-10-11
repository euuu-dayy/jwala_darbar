package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.DonationCampaignResponse;
import com.jwalanavdurga.service.DonationCampaignService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/donations/campaigns")
public class DonationCampaignController {

    private final DonationCampaignService donationCampaignService;

    public DonationCampaignController(
            DonationCampaignService donationCampaignService) {

        this.donationCampaignService =
                donationCampaignService;
    }

    @GetMapping
    public List<DonationCampaignResponse> getAllCampaigns() {

        return donationCampaignService.getAllCampaigns();
    }

    @GetMapping("/festival/{festivalId}")
    public List<DonationCampaignResponse> getCampaignsByFestival(
            @PathVariable Long festivalId) {

        return donationCampaignService
                .getCampaignsByFestival(festivalId);
    }

    @GetMapping("/active")
    public List<DonationCampaignResponse> getActiveCampaigns() {

        return donationCampaignService
                .getActiveCampaigns();
    }
}