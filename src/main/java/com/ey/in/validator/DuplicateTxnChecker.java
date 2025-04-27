package com.ey.in.validator;


import com.ey.in.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@Component
public  class DuplicateTxnChecker implements TransactionValidator {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean isValid(Transaction txn) {
        if(redisTemplate.hasKey(txn.getTransactionId())
                && computeTransactionHash(txn.getTransactionId(),txn.getUserId(),txn.getTimestamp())
                .equalsIgnoreCase(redisTemplate.opsForValue().get(txn.getTransactionId())))// Transaction ID already exists in Redis, indicating a duplicate transaction
            return false;

        redisTemplate.opsForValue()
                .set(txn.getTransactionId(), computeTransactionHash(txn.getTransactionId(),txn.getUserId(),txn.getTimestamp()), 1, TimeUnit.HOURS); // Store the transaction ID in Redis with a TTL of 1 minute
        return true;
    }

    public static String computeTransactionHash(String transactionId, String userId, Timestamp timestamp) {
        try {
            String input = transactionId + userId + timestamp;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error computing hash", e);
        }
    }
}

