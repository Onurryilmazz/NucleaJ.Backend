package com.nuclea.common.service.jwt;

import com.nuclea.common.config.JwtProperties;
import com.nuclea.common.constants.SecurityClaimKeys;
import com.nuclea.common.model.JwtTokenPair;
import com.nuclea.common.service.marker.ISingletonService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWT service implementation using jjwt library.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService implements IJwtService, ISingletonService {

    private final JwtProperties jwtProperties;

    /**
     * Get signing key from secret.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateAccessToken(Long userId, String email, Map<String, Object> claims) {
        Map<String, Object> tokenClaims = new HashMap<>();
        if (claims != null) {
            tokenClaims.putAll(claims);
        }

        tokenClaims.put(SecurityClaimKeys.USER_ID, userId.toString());
        tokenClaims.put(SecurityClaimKeys.EMAIL, email);
        tokenClaims.put(SecurityClaimKeys.AUTHENTICATED, "true");

        LocalDateTime expiryTime = LocalDateTime.now()
                .plusMinutes(jwtProperties.getAccessTokenExpirationMinutes());

        return Jwts.builder()
                .claims(tokenClaims)
                .subject(userId.toString())
                .issuer(jwtProperties.getIssuer())
                .audience().add(jwtProperties.getAudience()).and()
                .issuedAt(new Date())
                .expiration(Date.from(expiryTime.atZone(ZoneId.systemDefault()).toInstant()))
                .id(UUID.randomUUID().toString())
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(Long userId) {
        LocalDateTime expiryTime = LocalDateTime.now()
                .plusDays(jwtProperties.getRefreshTokenExpirationDays());

        return Jwts.builder()
                .subject(userId.toString())
                .issuer(jwtProperties.getIssuer())
                .audience().add(jwtProperties.getAudience()).and()
                .issuedAt(new Date())
                .expiration(Date.from(expiryTime.atZone(ZoneId.systemDefault()).toInstant()))
                .id(UUID.randomUUID().toString())
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public JwtTokenPair generateTokenPair(Long userId, String email, Map<String, Object> claims) {
        String accessToken = generateAccessToken(userId, email, claims);
        String refreshToken = generateRefreshToken(userId);

        LocalDateTime accessTokenExpiry = LocalDateTime.now()
                .plusMinutes(jwtProperties.getAccessTokenExpirationMinutes());
        LocalDateTime refreshTokenExpiry = LocalDateTime.now()
                .plusDays(jwtProperties.getRefreshTokenExpirationDays());

        return JwtTokenPair.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiry(accessTokenExpiry)
                .refreshTokenExpiry(refreshTokenExpiry)
                .build();
    }

    @Override
    public Claims validateToken(String token) {
        try {
            JwtParser parser = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build();

            return parser.parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            log.warn("Token expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.error("Malformed JWT token: {}", e.getMessage());
            throw e;
        } catch (SecurityException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = validateToken(token);
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            log.error("Error checking token expiration: {}", e.getMessage());
            return true;
        }
    }

    @Override
    public Long extractUserId(String token) {
        Claims claims = validateToken(token);
        String userId = claims.getSubject();
        return userId != null ? Long.parseLong(userId) : null;
    }

    @Override
    public String extractEmail(String token) {
        Claims claims = validateToken(token);
        return claims.get(SecurityClaimKeys.EMAIL, String.class);
    }

    @Override
    public Object extractClaim(String token, String claimKey) {
        Claims claims = validateToken(token);
        return claims.get(claimKey);
    }
}
