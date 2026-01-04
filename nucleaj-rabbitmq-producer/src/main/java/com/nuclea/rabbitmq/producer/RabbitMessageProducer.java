package com.nuclea.rabbitmq.producer;

import com.nuclea.rabbitmq.model.BaseRabbitMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * RabbitMQ message producer service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Send message to RabbitMQ exchange.
     */
    public void sendMessage(String exchange, String routingKey, BaseRabbitMessage message) {
        log.info("Sending message to exchange {} with routing key {}: {}", exchange, routingKey, message.getId());

        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            log.info("Message sent successfully: {}", message.getId());
        } catch (Exception e) {
            log.error("Failed to send message: {}", message.getId(), e);
        }
    }

    /**
     * Send message to queue directly.
     */
    public void sendMessageToQueue(String queueName, BaseRabbitMessage message) {
        log.info("Sending message to queue {}: {}", queueName, message.getId());

        try {
            rabbitTemplate.convertAndSend(queueName, message);
            log.info("Message sent successfully: {}", message.getId());
        } catch (Exception e) {
            log.error("Failed to send message: {}", message.getId(), e);
        }
    }
}
