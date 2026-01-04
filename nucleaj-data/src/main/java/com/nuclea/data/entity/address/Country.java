package com.nuclea.data.entity.address;

import com.nuclea.data.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Country entity.
 */
@Entity
@Table(name = "countries", indexes = {
        @Index(name = "idx_country_code", columnList = "code", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 10)
    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code; // ISO code: "TR", "US"

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 10)
    @Column(name = "phone_code", length = 10)
    private String phoneCode; // "+90", "+1"

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
