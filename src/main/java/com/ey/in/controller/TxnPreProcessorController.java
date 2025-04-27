package com.ey.in.controller;

import com.ey.in.service.TxnPreProcessorService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.ey.in.model.Transaction;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TxnPreProcessorController {

    @Autowired
    TxnPreProcessorService transactionPreProcessorService;

    @PostMapping("/preprocessTransaction")
    public ResponseEntity<String> preProcessTransaction(@RequestBody List<Transaction> transaction) {
        transactionPreProcessorService.preProcessTransaction(transaction);
       return ResponseEntity.ok("Transaction processed successfully");
    }

    @PostMapping(value = "/stubTransaction" , consumes = {"multipart/form-data"})
    public ResponseEntity<String> stubTransaction(@RequestPart("file")MultipartFile file) {
        List<Transaction> transactions = new ArrayList<>();
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<Transaction> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Transaction.class);
            CsvToBean<Transaction> csvToBean = new CsvToBeanBuilder<Transaction>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            transactions = csvToBean.parse().stream().map(csvLine ->
                Transaction.builder()
                        .transactionId(csvLine.getTransactionId())
                        .userId(csvLine.getUserId())
                        .amount(csvLine.getAmount())
                        .currency(csvLine.getCurrency())
                        .merchantId(csvLine.getMerchantId())
                        .location(csvLine.getLocation())
                        .userRiskScore(csvLine.getUserRiskScore())
                        .merchantRiskScore(csvLine.getMerchantRiskScore())
                        .timestamp(csvLine.getTimestamp()).build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file", e);
        }
        transactionPreProcessorService.stubTransaction(transactions);
        return ResponseEntity.ok("Transactions stubbed successfully");
    }
}
