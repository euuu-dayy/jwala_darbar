package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.VolunteerApplicationRequest;
import com.jwalanavdurga.dto.VolunteerApplicationResponse;
import com.jwalanavdurga.entity.VolunteerApplication;
import com.jwalanavdurga.exception.BusinessException;
import com.jwalanavdurga.repository.VolunteerApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VolunteerApplicationService {

    private static final Logger log =
            LoggerFactory.getLogger(VolunteerApplicationService.class);

    private final VolunteerApplicationRepository repository;

    public VolunteerApplicationService(
            VolunteerApplicationRepository repository) {

        this.repository = repository;
    }

    public VolunteerApplicationResponse apply(
            Long festivalId,
            VolunteerApplicationRequest request) {

        boolean alreadyApplied =
                repository.existsByMobileNumberAndDeletedAtIsNull(
                        request.getMobileNumber()
                );

        if (alreadyApplied) {

            log.warn(
                    "Duplicate volunteer application attempt | festivalId={}",
                    festivalId
            );

            throw new BusinessException(
                    "A volunteer application already exists for this mobile number."
            );
        }

        VolunteerApplication application =
                new VolunteerApplication();

        application.setFestivalId(festivalId);
        application.setName(request.getName());
        application.setMobileNumber(request.getMobileNumber());
        application.setEmail(request.getEmail());
        application.setAge(request.getAge());
        application.setPreferredArea(request.getPreferredArea());
        application.setMessage(request.getMessage());

        application.setStatus("PENDING");

        LocalDateTime now = LocalDateTime.now();

        application.setCreatedAt(now);
        application.setUpdatedAt(now);

        VolunteerApplication saved =
                repository.save(application);

        log.info(
                "Volunteer application created successfully | applicationId={} festivalId={}",
                saved.getId(),
                festivalId
        );

        return mapToResponse(saved);
    }

    public List<VolunteerApplicationResponse> getByFestival(
            Long festivalId) {

        log.info(
                "Fetching volunteer applications | festivalId={}",
                festivalId
        );

        return repository
                .findByFestivalIdAndDeletedAtIsNullOrderByCreatedAtDesc(
                        festivalId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private VolunteerApplicationResponse mapToResponse(
            VolunteerApplication application) {

        VolunteerApplicationResponse response =
                new VolunteerApplicationResponse();

        response.setId(application.getId());
        response.setFestivalId(application.getFestivalId());
        response.setName(application.getName());
        response.setMobileNumber(application.getMobileNumber());
        response.setEmail(application.getEmail());
        response.setAge(application.getAge());
        response.setPreferredArea(application.getPreferredArea());
        response.setMessage(application.getMessage());
        response.setStatus(application.getStatus());
        response.setCreatedAt(application.getCreatedAt());

        return response;
    }
}