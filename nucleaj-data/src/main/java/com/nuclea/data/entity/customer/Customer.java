package com.nuclea.data.entity.customer;

import com.nuclea.data.entity.base.BaseEntity;
import com.nuclea.data.entity.base.ISoftDelete;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Customer entity for end-user customers.
 * Supports OAuth authentication (Google, Apple).
 */
@Entity
@Table(name = "customers", indexes = {
        @Index(name = "idx_customer_email", columnList = "email"),
        @Index(name = "idx_customer_google_id", columnList = "google_id"),
        @Index(name = "idx_customer_apple_id", columnList = "apple_id"),
        @Index(name = "idx_customer_deleted_date", columnList = "deleted_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends BaseEntity implements ISoftDelete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Size(max = 255)
    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    @Column(name = "is_email_verified", nullable = false)
    @Builder.Default
    private Boolean isEmailVerified = false;

    @Size(max = 10)
    @Column(name = "email_verification_code", length = 10)
    private String emailVerificationCode;

    @Column(name = "email_verification_code_expiry")
    private LocalDateTime emailVerificationCodeExpiry;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    // OAuth fields
    @Size(max = 255)
    @Column(name = "google_id", length = 255)
    private String googleId;

    @Size(max = 255)
    @Column(name = "apple_id", length = 255)
    private String appleId;

    @Column(name = "oauth_provider", length = 50)
    private String oauthProvider; // "Google", "Apple", null for regular registration

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Transient
    public boolean isOAuthUser() {
        return oauthProvider != null && !oauthProvider.isBlank();
    }
}
