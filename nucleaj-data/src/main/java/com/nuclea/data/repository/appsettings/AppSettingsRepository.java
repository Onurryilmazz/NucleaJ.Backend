package com.nuclea.data.repository.appsettings;

import com.nuclea.data.entity.appsettings.AppSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for AppSettings entity.
 */
@Repository
public interface AppSettingsRepository extends JpaRepository<AppSettings, Long> {

    Optional<AppSettings> findBySettingKey(String settingKey);

    Optional<AppSettings> findBySettingKeyAndIsActiveTrue(String settingKey);

    List<AppSettings> findAllByIsActiveTrueOrderByDisplayOrder();

    List<AppSettings> findByGroupIdAndIsActiveTrue(Long groupId);
}
