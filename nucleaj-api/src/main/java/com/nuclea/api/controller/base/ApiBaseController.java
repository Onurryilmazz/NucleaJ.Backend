package com.nuclea.api.controller.base;

import com.nuclea.common.model.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Base controller providing common functionality for all API controllers.
 * Equivalent to C# ApiBaseController.
 */
@Slf4j
public abstract class ApiBaseController {

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * Success response with data.
     */
    protected <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    /**
     * Success response with data and message.
     */
    protected <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(ApiResponse.success(data, message));
    }

    /**
     * Error response with message.
     */
    protected <T> ResponseEntity<ApiResponse<T>> error(String message) {
        return ResponseEntity.badRequest().body(ApiResponse.error(message));
    }

    /**
     * Error response with message and status code.
     */
    protected <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.error(message));
    }

    /**
     * Validation error response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> invalidModel(BindingResult bindingResult) {
        String errors = bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return error(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Safe execute wrapper with exception handling.
     */
    protected <T> ResponseEntity<ApiResponse<T>> safeExecute(Supplier<ResponseEntity<ApiResponse<T>>> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            log.warn("Validation error: {}", e.getMessage());
            return error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error executing action: {}", e.getMessage(), e);
            String traceId = getTraceId();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(getMessage("General.InternalServerError"), traceId));
        }
    }

    /**
     * Get localized message.
     */
    protected String getMessage(String key) {
        return messageSource.getMessage(key, null, getCurrentLocale());
    }

    /**
     * Get localized message with parameters.
     */
    protected String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, getCurrentLocale());
    }

    /**
     * Get current locale from request.
     */
    protected Locale getCurrentLocale() {
        if (request != null) {
            String lang = request.getHeader("Accept-Language");
            if (lang != null && !lang.isBlank()) {
                return new Locale(lang.split(",")[0].split("-")[0]);
            }
        }
        return new Locale("tr"); // Default Turkish
    }

    /**
     * Set cookie in response.
     */
    protected void setCookie(String name, String value, int maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        cookie.setPath("/");
        cookie.setSecure(false); // Set to true in production with HTTPS
        response.addCookie(cookie);
    }

    /**
     * Get cookie value from request.
     */
    protected String getCookieValue(String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Delete cookie.
     */
    protected void deleteCookie(String name) {
        setCookie(name, "", 0, true);
    }

    /**
     * Get trace ID from request or generate new one.
     */
    protected String getTraceId() {
        String traceId = request.getHeader("X-Trace-Id");
        if (traceId == null || traceId.isBlank()) {
            traceId = java.util.UUID.randomUUID().toString();
        }
        return traceId;
    }

    /**
     * Get client IP address.
     */
    protected String getClientIpAddress() {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isBlank()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }

    /**
     * Get user agent from request.
     */
    protected String getUserAgent() {
        return request.getHeader("User-Agent");
    }
}
