package com.nuclea.scheduler.business.jobs;

import com.nuclea.data.repository.authentication.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Job for cleaning up expired refresh tokens.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupJob {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Clean up expired tokens daily at 2 AM.
     */
    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void cleanupExpiredTokens() {
        log.info("Starting expired token cleanup job...");

        LocalDateTime expirationDate = LocalDateTime.now();
        refreshTokenRepository.cleanupExpiredTokens(expirationDate, LocalDateTime.now());

        log.info("Expired token cleanup job completed");
    }
}
