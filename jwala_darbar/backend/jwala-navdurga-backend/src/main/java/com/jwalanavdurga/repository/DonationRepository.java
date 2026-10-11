package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository
        extends JpaRepository<Donation, Long> {

    List<Donation> findByCampaignIdAndStatusOrderByDonatedAtDesc(
            Long campaignId,
            String status
    );

    List<Donation> findByDonorIdOrderByDonatedAtDesc(
            Long donorId
    );
}