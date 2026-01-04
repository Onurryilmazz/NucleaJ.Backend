package com.nuclea.data.repository.authentication;

import com.nuclea.data.entity.authentication.RefreshToken;
import com.nuclea.data.repository.base.SoftDeleteRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for RefreshToken entity.
 */
@Repository
public interface RefreshTokenRepository extends SoftDeleteRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenAndDeletedDateIsNull(String token);

    List<RefreshToken> findByCustomerIdAndDeletedDateIsNull(Long customerId);

    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.revokedAt = :revokedAt WHERE rt.customer.id = :customerId AND rt.deletedDate IS NULL")
    void revokeAllByCustomerId(@Param("customerId") Long customerId, @Param("revokedAt") LocalDateTime revokedAt);

    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.deletedDate = :deletedDate WHERE rt.expiresAt < :expirationDate")
    void cleanupExpiredTokens(@Param("expirationDate") LocalDateTime expirationDate, @Param("deletedDate") LocalDateTime deletedDate);
}
