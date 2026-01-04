package com.nuclea.data.entity.common;

import com.nuclea.data.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * URL mapping entity for URL routing and redirects.
 */
@Entity
@Table(name = "url_mappings", indexes = {
        @Index(name = "idx_url_mapping_source", columnList = "source_url"),
        @Index(name = "idx_url_mapping_target", columnList = "target_url")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlMapping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 1000)
    @Column(name = "source_url", nullable = false, length = 1000)
    private String sourceUrl;

    @NotBlank
    @Size(max = 1000)
    @Column(name = "target_url", nullable = false, length = 1000)
    private String targetUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code_id")
    private UrlMappingStatusCode statusCode;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
