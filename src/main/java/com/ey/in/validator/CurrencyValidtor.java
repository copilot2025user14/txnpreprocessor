package com.ey.in.validator;

import com.ey.in.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CurrencyValidtor implements TransactionValidator {

    private static final String[] ALLOWED_CURRENCIES = {"INR", "USD", "EUR", "GBP", "JPY"};

    @Override
    public boolean isValid(Transaction transaction) {

        return Arrays.asList(ALLOWED_CURRENCIES).contains(transaction.getCurrency().toUpperCase());
    }


}
