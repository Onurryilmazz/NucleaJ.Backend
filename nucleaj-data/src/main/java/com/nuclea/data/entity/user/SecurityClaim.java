package com.nuclea.data.entity.user;

import com.nuclea.data.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Security claim entity for permissions.
 */
@Entity
@Table(name = "security_claims", indexes = {
        @Index(name = "idx_security_claim_key", columnList = "claim_key", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityClaim extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "claim_key", nullable = false, unique = true, length = 100)
    private String claimKey;

    @Size(max = 255)
    @Column(name = "claim_value", length = 255)
    private String claimValue;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
