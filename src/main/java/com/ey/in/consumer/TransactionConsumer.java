package com.ey.in.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {

     @KafkaListener(topics = "transaction-validation", groupId = "fraud-detection-group")
     public void listen(String message) {
         System.out.println("Received Message: " + message);
     }

}
