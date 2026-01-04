package com.nuclea.data.repository.common;

import com.nuclea.data.entity.common.UrlMappingStatusCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for UrlMappingStatusCode entity.
 */
@Repository
public interface UrlMappingStatusCodeRepository extends JpaRepository<UrlMappingStatusCode, Long> {

    Optional<UrlMappingStatusCode> findByStatusCode(Integer statusCode);
}
