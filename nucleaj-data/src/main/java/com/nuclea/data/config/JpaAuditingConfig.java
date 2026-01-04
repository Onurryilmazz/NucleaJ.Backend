package com.nuclea.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Auditing configuration for automatic timestamp management.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
