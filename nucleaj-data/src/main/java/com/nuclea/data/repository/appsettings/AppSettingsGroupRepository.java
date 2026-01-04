package com.nuclea.data.repository.appsettings;

import com.nuclea.data.entity.appsettings.AppSettingsGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for AppSettingsGroup entity.
 */
@Repository
public interface AppSettingsGroupRepository extends JpaRepository<AppSettingsGroup, Long> {

    Optional<AppSettingsGroup> findByGroupKey(String groupKey);

    List<AppSettingsGroup> findAllByIsActiveTrueOrderByDisplayOrder();
}
