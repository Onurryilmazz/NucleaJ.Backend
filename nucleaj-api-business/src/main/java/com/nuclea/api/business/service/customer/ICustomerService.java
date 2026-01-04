package com.nuclea.api.business.service.customer;

import com.nuclea.api.business.model.request.CustomerLoginRequest;
import com.nuclea.api.business.model.request.CustomerRegistrationRequest;
import com.nuclea.api.business.model.response.AuthenticationResponse;
import com.nuclea.common.model.ServiceResult;

/**
 * Customer service interface.
 */
public interface ICustomerService {

    /**
     * Register new customer.
     */
    ServiceResult<AuthenticationResponse> registerCustomer(CustomerRegistrationRequest request);

    /**
     * Customer login.
     */
    ServiceResult<AuthenticationResponse> loginCustomer(CustomerLoginRequest request, String ipAddress, String userAgent);

    /**
     * Verify customer email.
     */
    ServiceResult<Boolean> verifyEmail(String email, String verificationCode);

    /**
     * Resend email verification code.
     */
    ServiceResult<Boolean> resendVerificationCode(String email);
}
