package com.nuclea.data.entity.common;

import com.nuclea.data.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * HTTP status codes for URL mappings.
 */
@Entity
@Table(name = "url_mapping_status_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlMappingStatusCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status_code", nullable = false, unique = true)
    private Integer statusCode; // 301, 302, 307, etc.

    @NotBlank
    @Size(max = 100)
    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
