package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.DonationCampaignResponse;
import com.jwalanavdurga.entity.DonationCampaign;
import com.jwalanavdurga.repository.DonationCampaignRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class DonationCampaignService {

    private static final Logger log =
            LoggerFactory.getLogger(DonationCampaignService.class);

    private final DonationCampaignRepository donationCampaignRepository;

    public DonationCampaignService(
            DonationCampaignRepository donationCampaignRepository) {

        this.donationCampaignRepository =
                donationCampaignRepository;
    }

    public List<DonationCampaignResponse> getAllCampaigns() {

        log.info("Fetching all published donation campaigns");

        List<DonationCampaignResponse> campaigns =
                donationCampaignRepository
                        .findByStatusAndDeletedAtIsNullOrderByStartDateAsc(
                                "ACTIVE"
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} published donation campaigns",
                campaigns.size()
        );

        return campaigns;
    }

    public List<DonationCampaignResponse> getCampaignsByFestival(
            Long festivalId) {

        log.info(
                "Fetching donation campaigns | festivalId={}",
                festivalId
        );

        List<DonationCampaignResponse> campaigns =
                donationCampaignRepository
                        .findByFestivalIdAndStatusAndDeletedAtIsNullOrderByStartDateAsc(
                                festivalId,
                                "ACTIVE"
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} donation campaigns | festivalId={}",
                campaigns.size(),
                festivalId
        );

        return campaigns;
    }

    public List<DonationCampaignResponse> getActiveCampaigns() {

        LocalDate today = LocalDate.now(
                ZoneId.of("Asia/Kolkata")
        );

        log.info(
                "Fetching active donation campaigns | date={}",
                today
        );

        List<DonationCampaignResponse> campaigns =
                donationCampaignRepository
                        .findActiveCampaigns(
                                "ACTIVE",
                                today
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} active donation campaigns | date={}",
                campaigns.size(),
                today
        );

        return campaigns;
    }

    private DonationCampaignResponse mapToResponse(
            DonationCampaign campaign) {

        DonationCampaignResponse response =
                new DonationCampaignResponse();

        response.setId(campaign.getId());
        response.setFestivalId(campaign.getFestivalId());
        response.setName(campaign.getName());
        response.setDescription(campaign.getDescription());
        response.setTargetAmount(campaign.getTargetAmount());
        response.setStartDate(campaign.getStartDate());
        response.setEndDate(campaign.getEndDate());
        response.setStatus(campaign.getStatus());

        return response;
    }
}