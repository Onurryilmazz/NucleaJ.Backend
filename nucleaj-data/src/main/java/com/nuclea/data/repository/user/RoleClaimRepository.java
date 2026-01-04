package com.nuclea.data.repository.user;

import com.nuclea.data.entity.user.RoleClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for RoleClaim entity.
 */
@Repository
public interface RoleClaimRepository extends JpaRepository<RoleClaim, Long> {

    List<RoleClaim> findByRoleId(Long roleId);

    List<RoleClaim> findBySecurityClaimId(Long securityClaimId);
}
