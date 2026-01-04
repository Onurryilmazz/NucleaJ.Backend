package com.nuclea.common.service.cache;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * Cache manager interface for abstraction over different cache providers.
 */
public interface ICacheManager {

    /**
     * Get value from cache.
     */
    <T> T get(String key, Class<T> type);

    /**
     * Set value in cache.
     */
    <T> void set(String key, T value);

    /**
     * Set value in cache with expiration.
     */
    <T> void set(String key, T value, Duration expiration);

    /**
     * Get or set pattern - get from cache or execute supplier and cache result.
     */
    <T> T getOrSet(String key, Class<T> type, Supplier<T> supplier);

    /**
     * Get or set with expiration.
     */
    <T> T getOrSet(String key, Class<T> type, Duration expiration, Supplier<T> supplier);

    /**
     * Remove from cache.
     */
    void remove(String key);

    /**
     * Remove all keys matching pattern.
     */
    void removePattern(String pattern);

    /**
     * Check if key exists.
     */
    boolean exists(String key);

    /**
     * Clear all cache.
     */
    void clear();
}
