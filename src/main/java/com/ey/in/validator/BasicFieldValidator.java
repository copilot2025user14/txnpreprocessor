package com.ey.in.validator;

import com.ey.in.model.Transaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


@Component
public class BasicFieldValidator implements TransactionValidator {

    @Override
    public boolean isValid(Transaction transaction) {
        return transaction.getTimestamp() != null
                && !StringUtils.isBlank(transaction.getTransactionId())
                && !StringUtils.isBlank(transaction.getUserId())
                && transaction.getAmount() > 0
                && !StringUtils.isBlank(transaction.getCurrency())
                && !StringUtils.isBlank(transaction.getMerchantId());
    }
}

