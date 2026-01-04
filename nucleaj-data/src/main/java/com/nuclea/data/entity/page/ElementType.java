package com.nuclea.data.entity.page;

import com.nuclea.data.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Element type entity for page elements.
 */
@Entity
@Table(name = "element_types", indexes = {
        @Index(name = "idx_element_type_key", columnList = "type_key", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElementType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "type_key", nullable = false, unique = true, length = 100)
    private String typeKey; // "text", "image", "html", "video", etc.

    @NotBlank
    @Size(max = 255)
    @Column(name = "type_name", nullable = false, length = 255)
    private String typeName;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
