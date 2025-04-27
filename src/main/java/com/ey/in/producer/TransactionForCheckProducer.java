package com.ey.in.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import com.ey.in.model.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

@Slf4j
@Component
public class TransactionForCheckProducer {

    final KafkaTemplate<String, String> kafkaTemplate;

    public TransactionForCheckProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void publishTransactions(List<Transaction> transactions, String topicName) {
        for(Transaction transaction : transactions) {
            try{
                String message = new ObjectMapper().writeValueAsString(transaction);
                kafkaTemplate.send(topicName, message);
            }catch (Exception e)   {
                log.error("Unable to publish message to topic: {}", topicName);
            }
            log.info("Publishing transaction to {}: {}", message, topicName);
        }
    }
}
