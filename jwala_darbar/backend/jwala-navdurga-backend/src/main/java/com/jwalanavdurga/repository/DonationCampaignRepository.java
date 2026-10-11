package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.DonationCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DonationCampaignRepository
        extends JpaRepository<DonationCampaign, Long> {

    List<DonationCampaign>
    findByStatusAndDeletedAtIsNullOrderByStartDateAsc(
            String status
    );

    List<DonationCampaign>
    findByFestivalIdAndStatusAndDeletedAtIsNullOrderByStartDateAsc(
            Long festivalId,
            String status
    );

    @Query("""
            SELECT c
            FROM DonationCampaign c
            WHERE c.status = :status
              AND c.deletedAt IS NULL
              AND c.startDate <= :date
              AND c.endDate >= :date
            ORDER BY c.startDate ASC
            """)
    List<DonationCampaign> findActiveCampaigns(
            @Param("status") String status,
            @Param("date") LocalDate date
    );
}