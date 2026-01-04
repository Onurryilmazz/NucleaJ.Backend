package com.nuclea.api.controller;

import com.nuclea.api.business.model.request.CustomerLoginRequest;
import com.nuclea.api.business.model.request.CustomerRegistrationRequest;
import com.nuclea.api.business.model.response.AuthenticationResponse;
import com.nuclea.api.business.service.customer.ICustomerService;
import com.nuclea.api.controller.base.ApiBaseController;
import com.nuclea.common.model.ApiResponse;
import com.nuclea.common.model.ServiceResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Customer controller for customer operations.
 */
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController extends ApiBaseController {

    private final ICustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> registerCustomer(
            @Valid @RequestBody CustomerRegistrationRequest request,
            BindingResult bindingResult
    ) {
        return safeExecute(() -> {
            if (bindingResult.hasErrors()) {
                return invalidModel(bindingResult);
            }

            ServiceResult<AuthenticationResponse> result = customerService.registerCustomer(request);

            if (result.isError()) {
                return error(result.getMessage());
            }

            return success(result.getData(), result.getMessage());
        });
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> loginCustomer(
            @Valid @RequestBody CustomerLoginRequest request,
            BindingResult bindingResult
    ) {
        return safeExecute(() -> {
            if (bindingResult.hasErrors()) {
                return invalidModel(bindingResult);
            }

            String ipAddress = getClientIpAddress();
            String userAgent = getUserAgent();

            ServiceResult<AuthenticationResponse> result = customerService.loginCustomer(request, ipAddress, userAgent);

            if (result.isError()) {
                return error(result.getMessage());
            }

            return success(result.getData(), result.getMessage());
        });
    }

    @PostMapping("/verify-email")
    public ResponseEntity<ApiResponse<Boolean>> verifyEmail(
            @RequestParam String email,
            @RequestParam String code
    ) {
        return safeExecute(() -> {
            ServiceResult<Boolean> result = customerService.verifyEmail(email, code);

            if (result.isError()) {
                return error(result.getMessage());
            }

            return success(result.getData(), result.getMessage());
        });
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<ApiResponse<Boolean>> resendVerificationCode(
            @RequestParam String email
    ) {
        return safeExecute(() -> {
            ServiceResult<Boolean> result = customerService.resendVerificationCode(email);

            if (result.isError()) {
                return error(result.getMessage());
            }

            return success(result.getData(), result.getMessage());
        });
    }
}
