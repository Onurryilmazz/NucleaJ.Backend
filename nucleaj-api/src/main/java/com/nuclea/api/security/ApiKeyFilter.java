package com.nuclea.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * API Key validation filter.
 * Validates X-API-Key header for all requests.
 */
@Component
@Slf4j
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${app.api.key:}")
    private String apiKey;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Skip API key validation for actuator endpoints
        String requestPath = request.getRequestURI();
        if (requestPath.startsWith("/actuator") || requestPath.startsWith("/swagger") ||
            requestPath.startsWith("/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestApiKey = request.getHeader("X-API-Key");

        if (apiKey == null || apiKey.isBlank() || apiKey.equals(requestApiKey)) {
            filterChain.doFilter(request, response);
        } else {
            log.warn("Invalid API key from IP: {}", request.getRemoteAddr());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\":false,\"message\":\"Invalid or missing API key\"}");
        }
    }
}
