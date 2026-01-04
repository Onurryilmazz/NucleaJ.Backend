package com.nuclea.data.repository.user;

import com.nuclea.data.entity.user.SecurityClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for SecurityClaim entity.
 */
@Repository
public interface SecurityClaimRepository extends JpaRepository<SecurityClaim, Long> {

    Optional<SecurityClaim> findByClaimKey(String claimKey);

    boolean existsByClaimKey(String claimKey);
}
