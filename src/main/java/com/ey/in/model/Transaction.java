package com.ey.in.model;

import com.opencsv.bean.CsvBindByName;
import java.sql.Timestamp;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Transaction {

    @CsvBindByName(column = "transactionId")
    private String transactionId;

    @CsvBindByName(column = "userId")
    private String userId;

    @CsvBindByName(column = "amount")
    private Double amount;

    @CsvBindByName(column = "currency")
    private String currency;

    @CsvBindByName(column = "timestamp")
    private Timestamp timestamp;

    @CsvBindByName(column = "merchantId")
    private String merchantId;

    @CsvBindByName(column = "location")
    private String location;

    private Double userRiskScore;

    private Double merchantRiskScore;

    private ValidationStatus validationStatus;

}
