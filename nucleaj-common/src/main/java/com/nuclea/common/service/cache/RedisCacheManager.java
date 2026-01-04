package com.nuclea.common.service.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuclea.common.service.marker.ISingletonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Redis-based cache manager.
 * Activated when Redis is configured.
 */
@Service
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
@RequiredArgsConstructor
@Slf4j
public class RedisCacheManager implements ICacheManager, ISingletonService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public <T> T get(String key, Class<T> type) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            if (value == null) {
                return null;
            }
            return objectMapper.convertValue(value, type);
        } catch (Exception e) {
            log.error("Error getting from Redis cache: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public <T> void set(String key, T value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("Error setting to Redis cache: {}", e.getMessage());
        }
    }

    @Override
    public <T> void set(String key, T value, Duration expiration) {
        try {
            redisTemplate.opsForValue().set(key, value, expiration);
        } catch (Exception e) {
            log.error("Error setting to Redis cache with expiration: {}", e.getMessage());
        }
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
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Error removing from Redis cache: {}", e.getMessage());
        }
    }

    @Override
    public void removePattern(String pattern) {
        try {
            Set<String> keys = redisTemplate.keys(pattern);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            log.error("Error removing pattern from Redis cache: {}", e.getMessage());
        }
    }

    @Override
    public boolean exists(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            log.error("Error checking existence in Redis cache: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public void clear() {
        try {
            Set<String> keys = redisTemplate.keys("*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
            }
            log.info("Redis cache cleared");
        } catch (Exception e) {
            log.error("Error clearing Redis cache: {}", e.getMessage());
        }
    }
}
