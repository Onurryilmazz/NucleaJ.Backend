package com.nuclea.data.entity.authentication;

import com.nuclea.data.entity.base.BaseEntity;
import com.nuclea.data.entity.base.ISoftDelete;
import com.nuclea.data.entity.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * RefreshToken entity for JWT refresh token management.
 * Tracks issued refresh tokens with metadata for security.
 */
@Entity
@Table(name = "refresh_tokens", indexes = {
        @Index(name = "idx_refresh_token_token", columnList = "token"),
        @Index(name = "idx_refresh_token_customer_id", columnList = "customer_id"),
        @Index(name = "idx_refresh_token_expiry", columnList = "expires_at"),
        @Index(name = "idx_refresh_token_deleted_date", columnList = "deleted_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends BaseEntity implements ISoftDelete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 500)
    @Column(name = "token", nullable = false, unique = true, length = 500)
    private String token;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @NotNull
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;

    @Size(max = 45)
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Size(max = 500)
    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Transient
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    @Transient
    public boolean isRevoked() {
        return revokedAt != null;
    }

    @Transient
    public boolean isValid() {
        return !isExpired() && !isRevoked() && !isDeleted();
    }

    public void revoke() {
        this.revokedAt = LocalDateTime.now();
    }
}
