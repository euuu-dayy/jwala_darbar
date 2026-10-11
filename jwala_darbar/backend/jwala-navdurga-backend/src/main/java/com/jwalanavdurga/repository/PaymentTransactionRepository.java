package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentTransactionRepository
        extends JpaRepository<PaymentTransaction, Long> {

    List<PaymentTransaction> findByDonationIdOrderByInitiatedAtDesc(
            Long donationId
    );

    Optional<PaymentTransaction> findByGatewayAndGatewayTransactionId(
            String gateway,
            String gatewayTransactionId
    );

    boolean existsByDonationIdAndStatus(
            Long donationId,
            String status
    );
}