package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.VolunteerApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerApplicationRepository
        extends JpaRepository<VolunteerApplication, Long> {

    boolean existsByMobileNumberAndDeletedAtIsNull(
            String mobileNumber
    );

    List<VolunteerApplication>
    findByFestivalIdAndDeletedAtIsNullOrderByCreatedAtDesc(
            Long festivalId
    );

    List<VolunteerApplication>
    findByStatusAndDeletedAtIsNullOrderByCreatedAtDesc(
            String status
    );
}