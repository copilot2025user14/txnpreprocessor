package com.ey.in.consumer;

import com.ey.in.service.TxnPreProcessorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import com.ey.in.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


@Slf4j
@Component
public class TransactionConsumer {

    @Autowired
    TxnPreProcessorService txnPreProcessorService;

    @KafkaListener(topics = "transaction-validation-input", groupId = "fraud-detection-group")
    public void listen(List<String> message) {
        List<Transaction> txns = message.stream().map(m -> {
            try {
                return new ObjectMapper().readValue(m, Transaction.class);
            } catch (Exception e) {
                log.error("Unable to consume transaction from transaction-validation-input: {}", message, e);
                return null;
            }
        }).filter(Objects::nonNull).toList();
        txnPreProcessorService.preProcessTransaction(txns);
        log.info("Consumed {} message: {}", txns.size() , message);
    }

}
