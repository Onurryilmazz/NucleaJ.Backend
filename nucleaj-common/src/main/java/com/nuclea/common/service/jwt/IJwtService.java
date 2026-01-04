package com.nuclea.common.service.jwt;

import com.nuclea.common.model.JwtTokenPair;
import io.jsonwebtoken.Claims;

import java.util.Map;

/**
 * JWT service interface for token generation and validation.
 */
public interface IJwtService {

    /**
     * Generate access token with claims.
     */
    String generateAccessToken(Long userId, String email, Map<String, Object> claims);

    /**
     * Generate refresh token.
     */
    String generateRefreshToken(Long userId);

    /**
     * Generate token pair (access + refresh).
     */
    JwtTokenPair generateTokenPair(Long userId, String email, Map<String, Object> claims);

    /**
     * Validate token and extract claims.
     */
    Claims validateToken(String token);

    /**
     * Check if token is expired.
     */
    boolean isTokenExpired(String token);

    /**
     * Extract user ID from token.
     */
    Long extractUserId(String token);

    /**
     * Extract email from token.
     */
    String extractEmail(String token);

    /**
     * Extract claim from token.
     */
    Object extractClaim(String token, String claimKey);
}
