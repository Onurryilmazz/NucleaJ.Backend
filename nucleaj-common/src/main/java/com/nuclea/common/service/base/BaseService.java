package com.nuclea.common.service.base;

import com.nuclea.common.constants.SecurityClaimKeys;
import com.nuclea.common.service.cache.ICacheManager;
import com.nuclea.data.enums.LanguageEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;

/**
 * Base service class providing common functionality for all business services.
 * Equivalent to C# Nuclea.Common BaseService.
 */
@Getter
public abstract class BaseService {

    @Autowired
    protected ICacheManager cacheManager;

    @Autowired
    protected MessageSource messageSource;

    /**
     * Get current HTTP request.
     */
    protected HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    /**
     * Get current user ID from security context.
     */
    protected Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof Long) {
            return (Long) principal;
        }

        // Try to get from claims
        Object userIdClaim = authentication.getDetails();
        if (userIdClaim instanceof Long) {
            return (Long) userIdClaim;
        }

        return null;
    }

    /**
     * Get current user email from security context.
     */
    protected String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return authentication.getName();
    }

    /**
     * Check if current user is authenticated.
     */
    protected boolean isCurrentUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal());
    }

    /**
     * Get current language from request or default.
     */
    protected String getCurrentLanguage() {
        HttpServletRequest request = getCurrentRequest();
        if (request != null) {
            String lang = request.getHeader("Accept-Language");
            if (lang != null && !lang.isBlank()) {
                return lang.split(",")[0].split("-")[0].toLowerCase();
            }
        }
        return LanguageEnum.TR.getCode(); // Default to Turkish
    }

    /**
     * Get current language as enum.
     */
    protected LanguageEnum getCurrentLanguageEnum() {
        return LanguageEnum.fromCode(getCurrentLanguage());
    }

    /**
     * Get localized message by key.
     */
    protected String getMessage(String key) {
        return messageSource.getMessage(key, null, new Locale(getCurrentLanguage()));
    }

    /**
     * Get localized message with parameters.
     */
    protected String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, new Locale(getCurrentLanguage()));
    }

    /**
     * Get caller method name for logging.
     */
    protected String getMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 2) {
            return stackTrace[2].getMethodName();
        }
        return "Unknown";
    }
}
