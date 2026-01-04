package com.nuclea.data.entity.page;

import com.nuclea.data.entity.base.BaseEntity;
import com.nuclea.data.entity.base.ISoftDelete;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Page entity for dynamic pages with SEO metadata.
 */
@Entity
@Table(name = "pages", indexes = {
        @Index(name = "idx_page_slug", columnList = "slug", unique = true),
        @Index(name = "idx_page_deleted_date", columnList = "deleted_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Page extends BaseEntity implements ISoftDelete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @NotBlank
    @Size(max = 255)
    @Column(name = "slug", nullable = false, unique = true, length = 255)
    private String slug;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Size(max = 500)
    @Column(name = "meta_title", length = 500)
    private String metaTitle;

    @Size(max = 1000)
    @Column(name = "meta_description", length = 1000)
    private String metaDescription;

    @Size(max = 500)
    @Column(name = "meta_keywords", length = 500)
    private String metaKeywords;

    @Column(name = "is_published", nullable = false)
    @Builder.Default
    private Boolean isPublished = false;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;
}
