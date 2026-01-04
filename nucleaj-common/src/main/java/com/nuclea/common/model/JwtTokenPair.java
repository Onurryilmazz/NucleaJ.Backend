package com.nuclea.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * JWT token pair (access token + refresh token).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokenPair {

    private String accessToken;
    private String refreshToken;
    private LocalDateTime accessTokenExpiry;
    private LocalDateTime refreshTokenExpiry;
}
