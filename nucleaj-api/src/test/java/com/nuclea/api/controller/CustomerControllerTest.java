package com.nuclea.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuclea.api.business.model.request.CustomerLoginRequest;
import com.nuclea.api.business.model.request.CustomerRegistrationRequest;
import com.nuclea.api.business.model.response.AuthenticationResponse;
import com.nuclea.api.business.service.customer.ICustomerService;
import com.nuclea.common.model.ServiceResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for CustomerController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ICustomerService customerService;

    private CustomerRegistrationRequest registrationRequest;
    private CustomerLoginRequest loginRequest;
    private AuthenticationResponse authResponse;

    @BeforeEach
    void setUp() {
        registrationRequest = CustomerRegistrationRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("SecurePassword123!")
                .build();

        loginRequest = CustomerLoginRequest.builder()
                .email("john.doe@example.com")
                .password("SecurePassword123!")
                .build();

        authResponse = AuthenticationResponse.builder()
                .accessToken("mock-access-token")
                .refreshToken("mock-refresh-token")
                .build();
    }

    @Test
    void registerCustomer_Success() throws Exception {
        // Given
        when(customerService.registerCustomer(any(CustomerRegistrationRequest.class)))
                .thenReturn(ServiceResult.success(authResponse, "Registration successful"));

        // When & Then
        mockMvc.perform(post("/customer/register")
                        .header("X-API-Key", "test-api-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result.accessToken").value("mock-access-token"));
    }

    @Test
    void registerCustomer_ValidationError() throws Exception {
        // Given - invalid request (no email)
        CustomerRegistrationRequest invalidRequest = CustomerRegistrationRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .password("SecurePassword123!")
                .build();

        // When & Then
        mockMvc.perform(post("/customer/register")
                        .header("X-API-Key", "test-api-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginCustomer_Success() throws Exception {
        // Given
        when(customerService.loginCustomer(any(CustomerLoginRequest.class), anyString(), anyString()))
                .thenReturn(ServiceResult.success(authResponse));

        // When & Then
        mockMvc.perform(post("/customer/login")
                        .header("X-API-Key", "test-api-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result.accessToken").exists());
    }

    @Test
    void loginCustomer_InvalidCredentials() throws Exception {
        // Given
        when(customerService.loginCustomer(any(CustomerLoginRequest.class), anyString(), anyString()))
                .thenReturn(ServiceResult.error("Invalid credentials"));

        // When & Then
        mockMvc.perform(post("/customer/login")
                        .header("X-API-Key", "test-api-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid credentials"));
    }

    @Test
    void verifyEmail_Success() throws Exception {
        // Given
        when(customerService.verifyEmail(anyString(), anyString()))
                .thenReturn(ServiceResult.success(true, "Email verified"));

        // When & Then
        mockMvc.perform(post("/customer/verify-email")
                        .header("X-API-Key", "test-api-key")
                        .param("email", "john.doe@example.com")
                        .param("code", "123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result").value(true));
    }
}
