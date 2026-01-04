package com.nuclea.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Base Kafka message model.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseKafkaMessage {

    private String id;
    private String type;
    private LocalDateTime timestamp;
    private Object payload;

    public static BaseKafkaMessage create(String type, Object payload) {
        return BaseKafkaMessage.builder()
                .id(java.util.UUID.randomUUID().toString())
                .type(type)
                .timestamp(LocalDateTime.now())
                .payload(payload)
                .build();
    }
}
