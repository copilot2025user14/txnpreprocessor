package com.ey.in.validator;

import com.ey.in.model.Transaction;

public interface TransactionValidator {
    boolean isValid(Transaction transaction);
}
