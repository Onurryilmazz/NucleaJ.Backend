package com.nuclea.api.business.service.auth;

import com.nuclea.api.business.model.response.AuthenticationResponse;
import com.nuclea.common.model.ServiceResult;

/**
 * Authentication service interface.
 */
public interface IAuthenticationService {

    /**
     * Refresh access token using refresh token.
     */
    ServiceResult<AuthenticationResponse> refreshToken(String refreshToken, String ipAddress, String userAgent);

    /**
     * Revoke refresh token (logout).
     */
    ServiceResult<Boolean> revokeToken(String refreshToken);

    /**
     * Revoke all tokens for a customer.
     */
    ServiceResult<Boolean> revokeAllCustomerTokens(Long customerId);
}
