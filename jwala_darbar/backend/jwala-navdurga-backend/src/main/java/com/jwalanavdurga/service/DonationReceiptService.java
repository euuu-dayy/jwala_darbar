package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.DonationReceiptResponse;
import com.jwalanavdurga.entity.Donation;
import com.jwalanavdurga.entity.DonationReceipt;
import com.jwalanavdurga.exception.BusinessException;
import com.jwalanavdurga.exception.ResourceNotFoundException;
import com.jwalanavdurga.repository.DonationReceiptRepository;
import com.jwalanavdurga.repository.DonationRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.UUID;

@Service
public class DonationReceiptService {

    private static final Logger log =
            LoggerFactory.getLogger(DonationReceiptService.class);

    private final DonationReceiptRepository receiptRepository;
    private final DonationRepository donationRepository;

    public DonationReceiptService(
            DonationReceiptRepository receiptRepository,
            DonationRepository donationRepository) {

        this.receiptRepository = receiptRepository;
        this.donationRepository = donationRepository;
    }

    @Transactional
    public DonationReceiptResponse generateReceipt(Long donationId) {

        log.info(
                "Generating donation receipt | donationId={}",
                donationId
        );

        Donation donation = donationRepository
                .findById(donationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Donation not found."
                        )
                );

        if (!"SUCCESS".equalsIgnoreCase(
                donation.getDonationStatus())) {

            throw new BusinessException(
                    "Receipt can be generated only for a successful donation."
            );
        }

        /*
         * Idempotency:
         * Don't generate another receipt if one already exists.
         */
        var existingReceipt =
                receiptRepository.findByDonationId(donationId);

        if (existingReceipt.isPresent()) {

            log.info(
                    "Receipt already exists | donationId={} | receiptId={}",
                    donationId,
                    existingReceipt.get().getId()
            );

            return mapToResponse(existingReceipt.get());
        }

        LocalDateTime now = LocalDateTime.now();

        DonationReceipt receipt = new DonationReceipt();

        receipt.setDonationId(donationId);

        receipt.setReceiptNumber(
                generateReceiptNumber()
        );

        receipt.setReceiptDate(now);

        /*
         * PDF generation will be added later.
         * For now this remains NULL.
         */
        receipt.setReceiptDocumentUrl(null);

        receipt.setCreatedAt(now);

        DonationReceipt saved =
                receiptRepository.save(receipt);

        log.info(
                "Donation receipt generated | receiptId={} | donationId={}",
                saved.getId(),
                saved.getDonationId()
        );

        return mapToResponse(saved);
    }

    public DonationReceiptResponse getReceiptByDonationId(
            Long donationId) {

        if (!donationRepository.existsById(donationId)) {
            throw new ResourceNotFoundException(
                    "Donation not found."
            );
        }

        DonationReceipt receipt =
                receiptRepository
                        .findByDonationId(donationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Donation receipt not found."
                                )
                        );

        return mapToResponse(receipt);
    }

    private String generateReceiptNumber() {

        return "JNU-"
                + Year.now().getValue()
                + "-"
                + UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 12)
                        .toUpperCase();
    }

    private DonationReceiptResponse mapToResponse(
            DonationReceipt receipt) {

        DonationReceiptResponse response =
                new DonationReceiptResponse();

        response.setId(receipt.getId());
        response.setDonationId(receipt.getDonationId());
        response.setReceiptNumber(
                receipt.getReceiptNumber()
        );
        response.setReceiptDate(
                receipt.getReceiptDate()
        );
        response.setReceiptDocumentUrl(
                receipt.getReceiptDocumentUrl()
        );

        return response;
    }
}