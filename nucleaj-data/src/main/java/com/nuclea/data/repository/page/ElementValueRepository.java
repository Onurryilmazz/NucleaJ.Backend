package com.nuclea.data.repository.page;

import com.nuclea.data.entity.page.ElementValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for ElementValue entity.
 */
@Repository
public interface ElementValueRepository extends JpaRepository<ElementValue, ElementValue.ElementValueId> {

    @Query("SELECT ev FROM ElementValue ev WHERE ev.element.id = :elementId AND ev.language.id = :languageId AND ev.isActive = true")
    Optional<ElementValue> findByElementIdAndLanguageId(@Param("elementId") Long elementId, @Param("languageId") Long languageId);

    @Query("SELECT ev FROM ElementValue ev WHERE ev.element.id = :elementId AND ev.isActive = true")
    List<ElementValue> findByElementId(@Param("elementId") Long elementId);

    @Query("SELECT ev FROM ElementValue ev WHERE ev.language.id = :languageId AND ev.isActive = true")
    List<ElementValue> findByLanguageId(@Param("languageId") Long languageId);
}
