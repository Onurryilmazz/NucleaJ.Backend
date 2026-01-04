package com.nuclea.data.repository.common;

import com.nuclea.data.entity.common.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Language entity.
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    Optional<Language> findByCode(String code);

    Optional<Language> findByCodeAndIsActiveTrue(String code);

    List<Language> findAllByIsActiveTrueOrderByDisplayOrder();

    Optional<Language> findByIsDefaultTrue();
}
