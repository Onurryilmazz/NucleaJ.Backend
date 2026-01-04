package com.nuclea.data.repository.page;

import com.nuclea.data.entity.page.Page;
import com.nuclea.data.repository.base.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Page entity.
 */
@Repository
public interface PageRepository extends SoftDeleteRepository<Page, Long> {

    Optional<Page> findBySlugAndDeletedDateIsNull(String slug);

    List<Page> findByIsPublishedTrueAndDeletedDateIsNullOrderByDisplayOrder();
}
