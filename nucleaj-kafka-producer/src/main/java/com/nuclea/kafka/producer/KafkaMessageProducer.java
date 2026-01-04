package com.nuclea.kafka.producer;

import com.nuclea.kafka.model.BaseKafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Kafka message producer service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageProducer {

    private final KafkaTemplate<String, BaseKafkaMessage> kafkaTemplate;

    /**
     * Send message to Kafka topic.
     */
    public void sendMessage(String topic, BaseKafkaMessage message) {
        log.info("Sending message to topic {}: {}", topic, message.getId());

        kafkaTemplate.send(topic, message.getId(), message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Message sent successfully: {}", message.getId());
                    } else {
                        log.error("Failed to send message: {}", message.getId(), ex);
                    }
                });
    }

    /**
     * Send message with custom key.
     */
    public void sendMessage(String topic, String key, BaseKafkaMessage message) {
        log.info("Sending message to topic {} with key {}: {}", topic, key, message.getId());

        kafkaTemplate.send(topic, key, message);
    }
}
