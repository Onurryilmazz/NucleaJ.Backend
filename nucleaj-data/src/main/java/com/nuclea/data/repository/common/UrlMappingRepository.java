package com.nuclea.data.repository.common;

import com.nuclea.data.entity.common.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for UrlMapping entity.
 */
@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findBySourceUrlAndIsActiveTrue(String sourceUrl);

    List<UrlMapping> findAllByIsActiveTrueOrderBySourceUrl();
}
