package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.DonationReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonationReceiptRepository
        extends JpaRepository<DonationReceipt, Long> {

    Optional<DonationReceipt> findByDonationId(Long donationId);

    boolean existsByDonationId(Long donationId);
}