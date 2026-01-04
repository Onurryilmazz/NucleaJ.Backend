package com.nuclea.data.repository.page;

import com.nuclea.data.entity.page.ElementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for ElementType entity.
 */
@Repository
public interface ElementTypeRepository extends JpaRepository<ElementType, Long> {

    Optional<ElementType> findByTypeKey(String typeKey);

    List<ElementType> findAllByIsActiveTrueOrderByTypeName();
}
