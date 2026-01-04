package com.nuclea.data.entity.page;

import com.nuclea.data.entity.base.BaseEntity;
import com.nuclea.data.entity.base.ISoftDelete;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Element entity for page sections/components.
 */
@Entity
@Table(name = "elements", indexes = {
        @Index(name = "idx_element_key", columnList = "element_key", unique = true),
        @Index(name = "idx_element_page_id", columnList = "page_id"),
        @Index(name = "idx_element_type_id", columnList = "element_type_id"),
        @Index(name = "idx_element_deleted_date", columnList = "deleted_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Element extends BaseEntity implements ISoftDelete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "element_key", nullable = false, unique = true, length = 100)
    private String elementKey;

    @NotBlank
    @Size(max = 255)
    @Column(name = "element_name", nullable = false, length = 255)
    private String elementName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id")
    private Page page;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "element_type_id", nullable = false)
    private ElementType elementType;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;
}
