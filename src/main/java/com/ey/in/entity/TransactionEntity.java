package com.ey.in.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "location")
    private String location;

    @Column(name = "user_risk_score")
    private Double userRiskScore;

    @Column(name = "merchant_risk_score")
    private Double merchantRiskScore;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "validation_status")
    private String validationStatus;

    @Column(name = "event_status")
    private String eventStatus;

    public TransactionEntity(String transactionId, String userId, Double amount, String currency, String merchantId, String location, Double userRiskScore, Double merchantRiskScore, Timestamp timestamp, String validationStatus) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.merchantId = merchantId;
        this.location = location;
        this.userRiskScore = userRiskScore;
        this.merchantRiskScore = merchantRiskScore;
        this.timestamp = timestamp;
        this.validationStatus = validationStatus;
    }
}
