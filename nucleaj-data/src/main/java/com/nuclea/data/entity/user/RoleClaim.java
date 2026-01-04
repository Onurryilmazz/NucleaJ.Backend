package com.nuclea.data.entity.user;

import com.nuclea.data.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * RoleClaim entity - junction table between Role and SecurityClaim.
 */
@Entity
@Table(name = "role_claims", indexes = {
        @Index(name = "idx_role_claim_role_id", columnList = "role_id"),
        @Index(name = "idx_role_claim_security_claim_id", columnList = "security_claim_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_role_security_claim", columnNames = {"role_id", "security_claim_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleClaim extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "security_claim_id", nullable = false)
    private SecurityClaim securityClaim;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
