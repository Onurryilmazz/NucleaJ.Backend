package com.nuclea.business.caching.service;

import com.nuclea.common.constants.CacheKeys;
import com.nuclea.common.service.cache.ICacheManager;
import com.nuclea.common.service.marker.IScopedService;
import com.nuclea.data.entity.appsettings.AppSettings;
import com.nuclea.data.repository.appsettings.AppSettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * App settings caching service.
 * Caches all app settings on application startup.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AppSettingsCachingService implements IScopedService {

    private final AppSettingsRepository appSettingsRepository;
    private final ICacheManager cacheManager;

    /**
     * Cache all settings on application startup.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void cacheAllSettings() {
        log.info("Caching all app settings...");

        List<AppSettings> settings = appSettingsRepository.findAllByIsActiveTrueOrderByDisplayOrder();

        for (AppSettings setting : settings) {
            String cacheKey = CacheKeys.APP_SETTINGS_BY_KEY + setting.getSettingKey();
            cacheManager.set(cacheKey, setting, Duration.ofHours(24));
        }

        log.info("Cached {} app settings", settings.size());
    }

    /**
     * Get setting by key from cache or database.
     */
    public Optional<AppSettings> getSettingByKey(String key) {
        String cacheKey = CacheKeys.APP_SETTINGS_BY_KEY + key;

        return Optional.ofNullable(
                cacheManager.getOrSet(
                        cacheKey,
                        AppSettings.class,
                        Duration.ofHours(24),
                        () -> appSettingsRepository.findBySettingKeyAndIsActiveTrue(key).orElse(null)
                )
        );
    }

    /**
     * Get setting value as string.
     */
    public String getSettingValue(String key) {
        return getSettingByKey(key)
                .map(AppSettings::getSettingValue)
                .orElse(null);
    }

    /**
     * Clear app settings cache.
     */
    public void clearCache() {
        cacheManager.removePattern(CacheKeys.APP_SETTINGS_BY_KEY + "*");
        log.info("App settings cache cleared");
    }
}
