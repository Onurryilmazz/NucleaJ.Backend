package com.nuclea.common.model;

import com.nuclea.data.enums.ServiceResultType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Service layer result wrapper.
 *
 * @param <T> Result data type
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResult<T> {

    private ServiceResultType type;
    private String message;
    private T data;

    public static <T> ServiceResult<T> success(T data) {
        return ServiceResult.<T>builder()
                .type(ServiceResultType.SUCCESS)
                .data(data)
                .build();
    }

    public static <T> ServiceResult<T> success(T data, String message) {
        return ServiceResult.<T>builder()
                .type(ServiceResultType.SUCCESS)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ServiceResult<T> error(String message) {
        return ServiceResult.<T>builder()
                .type(ServiceResultType.ERROR)
                .message(message)
                .build();
    }

    public static <T> ServiceResult<T> warning(String message) {
        return ServiceResult.<T>builder()
                .type(ServiceResultType.WARNING)
                .message(message)
                .build();
    }

    public static <T> ServiceResult<T> warning(T data, String message) {
        return ServiceResult.<T>builder()
                .type(ServiceResultType.WARNING)
                .message(message)
                .data(data)
                .build();
    }

    public boolean isSuccess() {
        return type == ServiceResultType.SUCCESS;
    }

    public boolean isError() {
        return type == ServiceResultType.ERROR;
    }

    public boolean isWarning() {
        return type == ServiceResultType.WARNING;
    }
}
