package com.nuclea.api.business.service.auth;

import com.nuclea.api.business.mapper.CustomerMapper;
import com.nuclea.api.business.model.response.AuthenticationResponse;
import com.nuclea.api.business.model.response.CustomerResponse;
import com.nuclea.common.model.JwtTokenPair;
import com.nuclea.common.model.ServiceResult;
import com.nuclea.common.service.base.BaseService;
import com.nuclea.common.service.jwt.IJwtService;
import com.nuclea.common.service.marker.IScopedService;
import com.nuclea.data.entity.authentication.RefreshToken;
import com.nuclea.data.entity.customer.Customer;
import com.nuclea.data.repository.authentication.RefreshTokenRepository;
import com.nuclea.data.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Authentication service implementation.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService extends BaseService implements IAuthenticationService, IScopedService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomerRepository customerRepository;
    private final IJwtService jwtService;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public ServiceResult<AuthenticationResponse> refreshToken(String refreshToken, String ipAddress, String userAgent) {
        log.info("Refreshing access token");

        // Find refresh token
        RefreshToken token = refreshTokenRepository.findByTokenAndDeletedDateIsNull(refreshToken)
                .orElse(null);

        if (token == null) {
            return ServiceResult.error(getMessage("Auth.InvalidRefreshToken"));
        }

        // Validate refresh token
        if (token.isExpired()) {
            return ServiceResult.error(getMessage("Auth.RefreshTokenExpired"));
        }

        if (token.isRevoked()) {
            return ServiceResult.error(getMessage("Auth.RefreshTokenRevoked"));
        }

        // Get customer
        Customer customer = token.getCustomer();
        if (!customer.getIsActive()) {
            return ServiceResult.error(getMessage("Customer.AccountDeactivated"));
        }

        // Generate new tokens
        JwtTokenPair tokenPair = jwtService.generateTokenPair(
                customer.getId(),
                customer.getEmail(),
                buildCustomerClaims(customer)
        );

        // Revoke old refresh token
        token.revoke();
        refreshTokenRepository.save(token);

        // Save new refresh token
        RefreshToken newRefreshToken = RefreshToken.builder()
                .token(tokenPair.getRefreshToken())
                .customer(customer)
                .issuedAt(LocalDateTime.now())
                .expiresAt(tokenPair.getRefreshTokenExpiry())
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .build();
        refreshTokenRepository.save(newRefreshToken);

        // Build response
        CustomerResponse customerResponse = customerMapper.toResponse(customer);
        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .accessToken(tokenPair.getAccessToken())
                .refreshToken(tokenPair.getRefreshToken())
                .accessTokenExpiry(tokenPair.getAccessTokenExpiry())
                .refreshTokenExpiry(tokenPair.getRefreshTokenExpiry())
                .customer(customerResponse)
                .build();

        return ServiceResult.success(authResponse, getMessage("Auth.TokenRefreshSuccess"));
    }

    @Override
    @Transactional
    public ServiceResult<Boolean> revokeToken(String refreshToken) {
        log.info("Revoking refresh token");

        RefreshToken token = refreshTokenRepository.findByTokenAndDeletedDateIsNull(refreshToken)
                .orElse(null);

        if (token == null) {
            return ServiceResult.error(getMessage("Auth.InvalidRefreshToken"));
        }

        token.revoke();
        refreshTokenRepository.save(token);

        return ServiceResult.success(true, getMessage("Auth.LogoutSuccess"));
    }

    @Override
    @Transactional
    public ServiceResult<Boolean> revokeAllCustomerTokens(Long customerId) {
        log.info("Revoking all tokens for customer: {}", customerId);

        refreshTokenRepository.revokeAllByCustomerId(customerId, LocalDateTime.now());

        return ServiceResult.success(true);
    }

    /**
     * Build JWT claims for customer.
     */
    private Map<String, Object> buildCustomerClaims(Customer customer) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email_verified", customer.getIsEmailVerified());
        if (customer.getOauthProvider() != null) {
            claims.put("oauth_provider", customer.getOauthProvider());
        }
        return claims;
    }
}
