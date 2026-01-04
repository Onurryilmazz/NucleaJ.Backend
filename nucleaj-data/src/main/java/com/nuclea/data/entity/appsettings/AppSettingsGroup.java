package com.nuclea.data.entity.appsettings;

import com.nuclea.data.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * AppSettings group entity for organizing settings.
 */
@Entity
@Table(name = "app_settings_groups", indexes = {
        @Index(name = "idx_app_settings_group_key", columnList = "group_key", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppSettingsGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "group_key", nullable = false, unique = true, length = 100)
    private String groupKey;

    @NotBlank
    @Size(max = 255)
    @Column(name = "group_name", nullable = false, length = 255)
    private String groupName;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
