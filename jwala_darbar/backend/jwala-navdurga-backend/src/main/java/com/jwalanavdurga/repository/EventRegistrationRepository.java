package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRegistrationRepository
        extends JpaRepository<EventRegistration, Long> {

    List<EventRegistration> findByEventIdAndDeletedAtIsNull(
            Long eventId
    );

    long countByEventIdAndRegistrationStatusAndDeletedAtIsNull(
            Long eventId,
            String registrationStatus
    );

    boolean existsByEventIdAndMobileNumberAndDeletedAtIsNull(
            Long eventId,
            String mobileNumber
    );
}