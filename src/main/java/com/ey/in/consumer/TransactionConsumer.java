package com.ey.in.consumer;

import com.ey.in.producer.TransactionForCheckProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {

    final TransactionForCheckProducer transactionForCheckProducer;

    public TransactionConsumer(TransactionForCheckProducer transactionForCheckProducer) {
        this.transactionForCheckProducer = transactionForCheckProducer;
    }

    @KafkaListener(topics = "${transaction.validation.topic}", groupId = "${spring.kafka.group-id}")
    public void listen(String message) {
        System.out.println("Transaction received for validation: " + message);
        transactionForCheckProducer.sendMessage(message);
    }

}
