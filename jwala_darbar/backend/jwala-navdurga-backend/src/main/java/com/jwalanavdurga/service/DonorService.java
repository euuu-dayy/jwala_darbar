package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.DonorResponse;
import com.jwalanavdurga.entity.Donor;
import com.jwalanavdurga.repository.DonorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DonorService {

    private static final Logger log =
            LoggerFactory.getLogger(DonorService.class);

    private final DonorRepository donorRepository;

    public DonorService(
            DonorRepository donorRepository) {

        this.donorRepository = donorRepository;
    }

    public DonorResponse findOrCreateDonor(
            String name,
            String mobileNumber,
            String email) {

        log.info("Finding or creating donor");

        Donor donor = null;

        if (mobileNumber != null &&
                !mobileNumber.isBlank()) {

            donor = donorRepository
                    .findByMobileNumberAndDeletedAtIsNull(
                            mobileNumber
                    )
                    .orElse(null);
        }

        if (donor == null &&
                email != null &&
                !email.isBlank()) {

            donor = donorRepository
                    .findByEmailAndDeletedAtIsNull(
                            email
                    )
                    .orElse(null);
        }

        if (donor != null) {

            log.info(
                    "Existing donor found | donorId={}",
                    donor.getId()
            );

            return mapToResponse(donor);
        }

        LocalDateTime now =
                LocalDateTime.now();

        donor = new Donor();

        donor.setName(name);
        donor.setMobileNumber(mobileNumber);
        donor.setEmail(email);
        donor.setCreatedAt(now);
        donor.setUpdatedAt(now);

        Donor saved =
                donorRepository.save(donor);

        log.info(
                "New donor created successfully | donorId={}",
                saved.getId()
        );

        return mapToResponse(saved);
    }

    private DonorResponse mapToResponse(
            Donor donor) {

        DonorResponse response =
                new DonorResponse();

        response.setId(donor.getId());
        response.setName(donor.getName());
        response.setMobileNumber(
                donor.getMobileNumber()
        );
        response.setEmail(
                donor.getEmail()
        );
        response.setCreatedAt(
                donor.getCreatedAt()
        );

        return response;
    }
}