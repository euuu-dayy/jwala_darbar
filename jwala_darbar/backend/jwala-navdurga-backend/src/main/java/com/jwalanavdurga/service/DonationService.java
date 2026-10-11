package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.DonationRequest;
import com.jwalanavdurga.dto.DonationResponse;
import com.jwalanavdurga.dto.DonorResponse;
import com.jwalanavdurga.entity.Donation;
import com.jwalanavdurga.entity.DonationCampaign;
import com.jwalanavdurga.exception.BusinessException;
import com.jwalanavdurga.exception.ResourceNotFoundException;
import com.jwalanavdurga.repository.DonationCampaignRepository;
import com.jwalanavdurga.repository.DonationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class DonationService {

    private static final Logger log =
            LoggerFactory.getLogger(DonationService.class);

    private final DonationRepository donationRepository;
    private final DonationCampaignRepository campaignRepository;
    private final DonorService donorService;

    public DonationService(
            DonationRepository donationRepository,
            DonationCampaignRepository campaignRepository,
            DonorService donorService) {

        this.donationRepository = donationRepository;
        this.campaignRepository = campaignRepository;
        this.donorService = donorService;
    }

    public DonationResponse createDonation(
            DonationRequest request) {

        log.info(
                "Creating donation | campaignId={} amount={}",
                request.getCampaignId(),
                request.getAmount()
        );

        DonationCampaign campaign =
                campaignRepository.findById(
                        request.getCampaignId()
                ).orElseThrow(() -> {

                    log.warn(
                            "Donation campaign not found | campaignId={}",
                            request.getCampaignId()
                    );

                    return new ResourceNotFoundException(
                            "Donation campaign not found."
                    );
                });

        LocalDate today = LocalDate.now(
                ZoneId.of("Asia/Kolkata")
        );

        if (!"ACTIVE".equalsIgnoreCase(
                campaign.getStatus())) {

            log.warn(
                    "Donation attempted for inactive campaign | campaignId={}",
                    campaign.getId()
            );

            throw new BusinessException(
                    "This donation campaign is not active."
            );
        }

        if (today.isBefore(campaign.getStartDate())
                || today.isAfter(campaign.getEndDate())) {

            log.warn(
                    "Donation attempted outside campaign period | campaignId={} date={}",
                    campaign.getId(),
                    today
            );

            throw new BusinessException(
                    "This donation campaign is not currently accepting donations."
            );
        }

        if (campaign.getTargetAmount() != null
                && request.getAmount()
                .compareTo(campaign.getTargetAmount()) > 0) {

            log.warn(
                    "Donation amount exceeds campaign target | campaignId={} amount={}",
                    campaign.getId(),
                    request.getAmount()
            );

            throw new BusinessException(
                    "Donation amount cannot exceed the campaign target."
            );
        }

        DonorResponse donor =
                donorService.findOrCreateDonor(
                        request.getDonorName(),
                        request.getMobileNumber(),
                        request.getEmail()
                );

        LocalDateTime now =
                LocalDateTime.now();

        Donation donation =
                new Donation();

        donation.setCampaignId(
                campaign.getId()
        );

        donation.setDonorId(
                donor.getId()
        );

        donation.setAmount(
                request.getAmount()
        );

        donation.setDonationType(
                request.getDonationType()
        );

        /*
         * Donation is initially pending.
         * It becomes successful only after
         * payment verification.
         */
        donation.setStatus("PENDING");
        donation.setDonationStatus("PENDING");

        donation.setNote(
                request.getNote()
        );

        donation.setDonatedAt(now);
        donation.setCreatedAt(now);
        donation.setUpdatedAt(now);

        Donation saved =
                donationRepository.save(donation);

        log.info(
                "Donation created successfully | donationId={} campaignId={} donorId={}",
                saved.getId(),
                saved.getCampaignId(),
                saved.getDonorId()
        );

        return mapToResponse(saved);
    }

    public List<DonationResponse> getDonationsByCampaign(
            Long campaignId) {

        log.info(
                "Fetching donations | campaignId={}",
                campaignId
        );

        return donationRepository
                .findByCampaignIdAndStatusOrderByDonatedAtDesc(
                        campaignId,
                        "SUCCESS"
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private DonationResponse mapToResponse(
            Donation donation) {

        DonationResponse response =
                new DonationResponse();

        response.setId(
                donation.getId()
        );

        response.setCampaignId(
                donation.getCampaignId()
        );

        response.setDonorId(
                donation.getDonorId()
        );

        response.setAmount(
                donation.getAmount()
        );

        response.setDonationType(
                donation.getDonationType()
        );

        response.setStatus(
                donation.getStatus()
        );

        response.setNote(
                donation.getNote()
        );

        response.setDonatedAt(
                donation.getDonatedAt()
        );

        return response;
    }
}