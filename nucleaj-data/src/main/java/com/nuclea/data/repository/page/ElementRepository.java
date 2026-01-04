package com.nuclea.data.repository.page;

import com.nuclea.data.entity.page.Element;
import com.nuclea.data.repository.base.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Element entity.
 */
@Repository
public interface ElementRepository extends SoftDeleteRepository<Element, Long> {

    Optional<Element> findByElementKeyAndDeletedDateIsNull(String elementKey);

    List<Element> findByPageIdAndDeletedDateIsNullOrderByDisplayOrder(Long pageId);

    List<Element> findByIsActiveTrueAndDeletedDateIsNullOrderByDisplayOrder();
}
