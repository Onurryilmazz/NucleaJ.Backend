package com.nuclea.api.business.service.customer;

import com.nuclea.api.business.mapper.CustomerMapper;
import com.nuclea.api.business.model.request.CustomerLoginRequest;
import com.nuclea.api.business.model.request.CustomerRegistrationRequest;
import com.nuclea.api.business.model.response.AuthenticationResponse;
import com.nuclea.api.business.model.response.CustomerResponse;
import com.nuclea.common.model.JwtTokenPair;
import com.nuclea.common.model.ServiceResult;
import com.nuclea.common.service.base.BaseService;
import com.nuclea.common.service.email.IMailService;
import com.nuclea.common.service.jwt.IJwtService;
import com.nuclea.common.service.marker.IScopedService;
import com.nuclea.common.util.KeyGenerator;
import com.nuclea.common.util.PasswordUtil;
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
 * Customer service implementation.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService extends BaseService implements ICustomerService, IScopedService {

    private final CustomerRepository customerRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final IJwtService jwtService;
    private final IMailService mailService;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public ServiceResult<AuthenticationResponse> registerCustomer(CustomerRegistrationRequest request) {
        log.info("Registering new customer: {}", request.getEmail());

        // Check if email already exists
        if (customerRepository.existsByEmailAndDeletedDateIsNull(request.getEmail())) {
            return ServiceResult.error(getMessage("Customer.EmailAlreadyExists"));
        }

        // Create customer
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .passwordHash(PasswordUtil.hashPassword(request.getPassword()))
                .isEmailVerified(false)
                .isActive(true)
                .emailVerificationCode(KeyGenerator.generateVerificationCode())
                .emailVerificationCodeExpiry(LocalDateTime.now().plusHours(24))
                .build();

        customer = customerRepository.save(customer);

        // Send verification email
        sendVerificationEmail(customer);

        // Generate tokens
        JwtTokenPair tokenPair = jwtService.generateTokenPair(
                customer.getId(),
                customer.getEmail(),
                buildCustomerClaims(customer)
        );

        // Save refresh token
        saveRefreshToken(customer, tokenPair.getRefreshToken(), null, null);

        // Build response
        CustomerResponse customerResponse = customerMapper.toResponse(customer);
        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .accessToken(tokenPair.getAccessToken())
                .refreshToken(tokenPair.getRefreshToken())
                .accessTokenExpiry(tokenPair.getAccessTokenExpiry())
                .refreshTokenExpiry(tokenPair.getRefreshTokenExpiry())
                .customer(customerResponse)
                .build();

        return ServiceResult.success(authResponse, getMessage("Customer.RegistrationSuccess"));
    }

    @Override
    @Transactional
    public ServiceResult<AuthenticationResponse> loginCustomer(CustomerLoginRequest request, String ipAddress, String userAgent) {
        log.info("Customer login attempt: {}", request.getEmail());

        // Find customer
        Customer customer = customerRepository.findByEmailAndDeletedDateIsNull(request.getEmail())
                .orElse(null);

        if (customer == null) {
            return ServiceResult.error(getMessage("Customer.InvalidCredentials"));
        }

        // Verify password
        if (!PasswordUtil.verifyPassword(request.getPassword(), customer.getPasswordHash())) {
            return ServiceResult.error(getMessage("Customer.InvalidCredentials"));
        }

        // Check if account is active
        if (!customer.getIsActive()) {
            return ServiceResult.error(getMessage("Customer.AccountDeactivated"));
        }

        // Update last login date
        customer.setLastLoginDate(LocalDateTime.now());
        customerRepository.save(customer);

        // Generate tokens
        JwtTokenPair tokenPair = jwtService.generateTokenPair(
                customer.getId(),
                customer.getEmail(),
                buildCustomerClaims(customer)
        );

        // Save refresh token
        saveRefreshToken(customer, tokenPair.getRefreshToken(), ipAddress, userAgent);

        // Build response
        CustomerResponse customerResponse = customerMapper.toResponse(customer);
        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .accessToken(tokenPair.getAccessToken())
                .refreshToken(tokenPair.getRefreshToken())
                .accessTokenExpiry(tokenPair.getAccessTokenExpiry())
                .refreshTokenExpiry(tokenPair.getRefreshTokenExpiry())
                .customer(customerResponse)
                .build();

        return ServiceResult.success(authResponse);
    }

    @Override
    @Transactional
    public ServiceResult<Boolean> verifyEmail(String email, String verificationCode) {
        log.info("Verifying email: {}", email);

        Customer customer = customerRepository.findByEmailAndDeletedDateIsNull(email)
                .orElse(null);

        if (customer == null) {
            return ServiceResult.error(getMessage("Customer.CustomerNotFound"));
        }

        if (customer.getIsEmailVerified()) {
            return ServiceResult.warning(true, getMessage("Customer.EmailAlreadyVerified"));
        }

        if (!verificationCode.equals(customer.getEmailVerificationCode())) {
            return ServiceResult.error(getMessage("Customer.EmailVerificationCodeInvalid"));
        }

        if (customer.getEmailVerificationCodeExpiry().isBefore(LocalDateTime.now())) {
            return ServiceResult.error(getMessage("Customer.EmailVerificationCodeExpired"));
        }

        customer.setIsEmailVerified(true);
        customer.setEmailVerificationCode(null);
        customer.setEmailVerificationCodeExpiry(null);
        customerRepository.save(customer);

        return ServiceResult.success(true, getMessage("Customer.EmailVerificationSuccess"));
    }

    @Override
    @Transactional
    public ServiceResult<Boolean> resendVerificationCode(String email) {
        log.info("Resending verification code: {}", email);

        Customer customer = customerRepository.findByEmailAndDeletedDateIsNull(email)
                .orElse(null);

        if (customer == null) {
            return ServiceResult.error(getMessage("Customer.CustomerNotFound"));
        }

        if (customer.getIsEmailVerified()) {
            return ServiceResult.warning(false, getMessage("Customer.EmailAlreadyVerified"));
        }

        customer.setEmailVerificationCode(KeyGenerator.generateVerificationCode());
        customer.setEmailVerificationCodeExpiry(LocalDateTime.now().plusHours(24));
        customerRepository.save(customer);

        sendVerificationEmail(customer);

        return ServiceResult.success(true, getMessage("Customer.EmailVerificationCodeSent"));
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

    /**
     * Save refresh token to database.
     */
    private void saveRefreshToken(Customer customer, String token, String ipAddress, String userAgent) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .customer(customer)
                .issuedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(7))
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    /**
     * Send email verification code.
     */
    private void sendVerificationEmail(Customer customer) {
        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("firstName", customer.getFirstName());
            variables.put("verificationCode", customer.getEmailVerificationCode());

            mailService.sendTemplateEmail(
                    customer.getEmail(),
                    "Email Verification",
                    "email-verification",
                    variables
            );
        } catch (Exception e) {
            log.error("Error sending verification email: {}", e.getMessage(), e);
        }
    }
}
