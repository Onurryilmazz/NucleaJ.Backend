package com.nuclea.data.entity.page;

import com.nuclea.data.entity.base.BaseEntity;
import com.nuclea.data.entity.common.Language;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

/**
 * ElementValue entity for multilingual element content.
 * Uses composite key (element_id + language_id).
 */
@Entity
@Table(name = "element_values", indexes = {
        @Index(name = "idx_element_value_element_id", columnList = "element_id"),
        @Index(name = "idx_element_value_language_id", columnList = "language_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ElementValue.ElementValueId.class)
public class ElementValue extends BaseEntity {

    @Id
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "element_id", nullable = false)
    private Element element;

    @Id
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "value", columnDefinition = "TEXT")
    private String value;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    /**
     * Composite key class for ElementValue.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ElementValueId implements Serializable {
        private Element element;
        private Language language;
    }
}
