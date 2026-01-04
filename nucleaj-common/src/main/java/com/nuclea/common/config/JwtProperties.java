package com.nuclea.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT configuration properties.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secretKey;
    private String issuer;
    private String audience;
    private Integer accessTokenExpirationMinutes = 60;
    private Integer refreshTokenExpirationDays = 7;
    private Boolean validateIssuer = true;
    private Boolean validateAudience = true;
    private Boolean validateLifetime = true;
    private Boolean validateIssuerSigningKey = true;
    private Integer clockSkewMinutes = 5;
}
