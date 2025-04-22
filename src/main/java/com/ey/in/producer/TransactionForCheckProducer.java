package com.ey.in.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionForCheckProducer {

    @Value(value = "${validation.topic:transaction-validation}")
    private String validationTopic;

    final KafkaTemplate<String, String> kafkaTemplate;

    public TransactionForCheckProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(validationTopic, message);
        System.out.println("Sending message: " + message);

    }
}
