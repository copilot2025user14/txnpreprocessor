package com.ey.in.service;

import com.ey.in.entity.TransactionEntity;
import com.ey.in.producer.TransactionForCheckProducer;
import com.ey.in.repository.TransactionRepository;
import com.ey.in.validator.TransactionValidator;
import lombok.extern.slf4j.Slf4j;
import com.ey.in.model.Transaction;
import com.ey.in.model.ValidationStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.List;


@Slf4j
@Service
public class TxnPreProcessorService {

    @Autowired
    List<TransactionValidator> txnValidators;
    @Autowired
    UserRiskService userRiskService;
    @Autowired
    MerchantRiskService merchantRiskService;
    @Autowired
    TransactionForCheckProducer kafkaPublisher;
    @Autowired
    TransactionRepository transactionRepo;

    @Value(value = "${validation.topic:transaction-valid-output}")
    private String validTxnTopic;

    @Value(value = "${validation.topic:transaction-invalid-output}")
    private String invalidTxnTopic;

    @Value(value = "${validation.topic:transaction-validation-input}")
    private String txnValidationInputTopic;



    public void preProcessTransaction(List<Transaction> transaction) {

        log.info("Pre-processing transaction: {}", transaction.stream().map(Transaction::getTransactionId));

        //Validate by different Validators
        for (Transaction txn : transaction) {
            txnValidators.forEach(
                    txnValidator -> {
                        txn.setValidationStatus(ValidationStatus.VALID);
                        if (!txnValidator.isValid(txn))
                            txn.setValidationStatus(ValidationStatus.INVALID);
                    }
            );
        }

        // Transaction Enrichment With Additional Data
        enrichTransaction(transaction);

        //Save & publish valid Transaction to fraud db
        List<Transaction> validTransaction = transaction.stream()
                .filter(t -> t.getValidationStatus() == ValidationStatus.VALID)
                .toList();
        List<TransactionEntity> txnEntities = validTransaction.stream()
                .map(txn -> new TransactionEntity(txn.getTransactionId(),
                        txn.getUserId(),
                        txn.getAmount(),
                        txn.getCurrency(),
                        txn.getMerchantId(),
                        txn.getLocation(),
                        txn.getUserRiskScore(),
                        txn.getMerchantRiskScore(),
                        txn.getTimestamp(),
                        txn.getValidationStatus().name()))
                .toList();
        transactionRepo.saveAll(txnEntities);
        kafkaPublisher.publishTransactions(validTransaction , validTxnTopic);

        //publish invalid Transaction to DLQ
        kafkaPublisher.publishTransactions( transaction.stream()
                .filter(t -> t.getValidationStatus() == ValidationStatus.INVALID)
                .toList(), invalidTxnTopic);

    }

    private void enrichTransaction(List<Transaction> transaction) {
        transaction.stream()
                .filter(t -> t.getValidationStatus().equals(ValidationStatus.VALID))
                .forEach(txn -> {
                    //Enrich the transaction with user risk score and merchant risk score and location
                    txn.setUserRiskScore(userRiskService.getUserRiskScore(txn.getUserId()));
                    txn.setMerchantRiskScore(merchantRiskService.getMerchantRiskScore(txn.getMerchantId()));
                    if (StringUtils.isBlank(txn.getLocation()))
                        txn.setLocation(txn.getLocation());
                });
    }


    public void stubTransaction(List<Transaction> transactions) {
        kafkaPublisher.publishTransactions(transactions,txnValidationInputTopic );
    }
}
