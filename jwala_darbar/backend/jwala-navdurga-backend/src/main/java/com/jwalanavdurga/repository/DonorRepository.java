package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonorRepository
        extends JpaRepository<Donor, Long> {

    Optional<Donor> findByMobileNumberAndDeletedAtIsNull(
            String mobileNumber
    );

    Optional<Donor> findByEmailAndDeletedAtIsNull(
            String email
    );
}