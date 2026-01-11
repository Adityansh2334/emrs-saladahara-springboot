package org.labs.jkcloud.emrs.repository;

import jakarta.transaction.Transactional;
import org.labs.jkcloud.emrs.model.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, String> {

    long countByEmailAndCreatedAtAfter(String email, LocalDateTime createdAt);
    @Transactional
    int deleteByExpiryTimeBeforeOrVerifiedIsTrue(LocalDateTime expiry);

    @Transactional
    void deleteAllByEmailAndVerified(String email, boolean verified);

}

