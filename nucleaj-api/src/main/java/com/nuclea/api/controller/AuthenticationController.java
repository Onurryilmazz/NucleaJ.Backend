package com.nuclea.api.controller;

import com.nuclea.api.business.model.request.RefreshTokenRequest;
import com.nuclea.api.business.model.response.AuthenticationResponse;
import com.nuclea.api.business.service.auth.IAuthenticationService;
import com.nuclea.api.controller.base.ApiBaseController;
import com.nuclea.common.model.ApiResponse;
import com.nuclea.common.model.ServiceResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller for token operations.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController extends ApiBaseController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request,
            BindingResult bindingResult
    ) {
        return safeExecute(() -> {
            if (bindingResult.hasErrors()) {
                return invalidModel(bindingResult);
            }

            String ipAddress = getClientIpAddress();
            String userAgent = getUserAgent();

            ServiceResult<AuthenticationResponse> result = authenticationService.refreshToken(
                    request.getRefreshToken(),
                    ipAddress,
                    userAgent
            );

            if (result.isError()) {
                return error(result.getMessage());
            }

            return success(result.getData(), result.getMessage());
        });
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Boolean>> logout(
            @Valid @RequestBody RefreshTokenRequest request,
            BindingResult bindingResult
    ) {
        return safeExecute(() -> {
            if (bindingResult.hasErrors()) {
                return invalidModel(bindingResult);
            }

            ServiceResult<Boolean> result = authenticationService.revokeToken(request.getRefreshToken());

            if (result.isError()) {
                return error(result.getMessage());
            }

            return success(result.getData(), result.getMessage());
        });
    }
}
