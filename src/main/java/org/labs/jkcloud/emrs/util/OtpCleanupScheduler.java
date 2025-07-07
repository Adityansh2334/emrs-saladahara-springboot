package org.labs.jkcloud.emrs.util;

import lombok.extern.slf4j.Slf4j;
import org.labs.jkcloud.emrs.repository.OtpVerificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class OtpCleanupScheduler {

    private final OtpVerificationRepository otpRepo;

    /** Runs every 30 minutes */
    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void cleanupExpiredOtps() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(15);
        int deleted = otpRepo.deleteByExpiryTimeBeforeOrVerifiedIsTrue(threshold);
        log.info("🧹 Cleaned up {} expired/verified OTPs.", deleted);
    }
}

