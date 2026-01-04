package com.nuclea.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Base RabbitMQ message model.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseRabbitMessage {

    private String id;
    private String type;
    private LocalDateTime timestamp;
    private Object payload;

    public static BaseRabbitMessage create(String type, Object payload) {
        return BaseRabbitMessage.builder()
                .id(java.util.UUID.randomUUID().toString())
                .type(type)
                .timestamp(LocalDateTime.now())
                .payload(payload)
                .build();
    }
}
