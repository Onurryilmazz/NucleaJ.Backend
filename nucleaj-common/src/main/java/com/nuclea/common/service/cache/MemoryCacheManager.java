package com.nuclea.common.service.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.nuclea.common.service.marker.ISingletonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * In-memory cache manager using Caffeine.
 * Used as default when Redis is not configured.
 */
@Service
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "caffeine", matchIfMissing = true)
@Slf4j
public class MemoryCacheManager implements ICacheManager, ISingletonService {

    private final Cache<String, Object> cache;
    private final ObjectMapper objectMapper;

    public MemoryCacheManager(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .recordStats()
                .build();
        log.info("MemoryCacheManager initialized with Caffeine");
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        try {
            Object value = cache.getIfPresent(key);
            if (value == null) {
                return null;
            }
            return objectMapper.convertValue(value, type);
        } catch (Exception e) {
            log.error("Error getting from cache: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public <T> void set(String key, T value) {
        cache.put(key, value);
    }

    @Override
    public <T> void set(String key, T value, Duration expiration) {
        // Caffeine doesn't support per-entry TTL, so we use the global one
        // For production, consider using Redis for per-key expiration
        cache.put(key, value);
        log.debug("Cached key: {} (global expiration applied)", key);
    }

    @Override
    public <T> T getOrSet(String key, Class<T> type, Supplier<T> supplier) {
        T cachedValue = get(key, type);
        if (cachedValue != null) {
            return cachedValue;
        }

        T value = supplier.get();
        if (value != null) {
            set(key, value);
        }
        return value;
    }

    @Override
    public <T> T getOrSet(String key, Class<T> type, Duration expiration, Supplier<T> supplier) {
        T cachedValue = get(key, type);
        if (cachedValue != null) {
            return cachedValue;
        }

        T value = supplier.get();
        if (value != null) {
            set(key, value, expiration);
        }
        return value;
    }

    @Override
    public void remove(String key) {
        cache.invalidate(key);
    }

    @Override
    public void removePattern(String pattern) {
        String regex = pattern.replace("*", ".*");
        cache.asMap().keySet().stream()
                .filter(key -> key.matches(regex))
                .forEach(cache::invalidate);
    }

    @Override
    public boolean exists(String key) {
        return cache.getIfPresent(key) != null;
    }

    @Override
    public void clear() {
        cache.invalidateAll();
        log.info("Cache cleared");
    }
}
