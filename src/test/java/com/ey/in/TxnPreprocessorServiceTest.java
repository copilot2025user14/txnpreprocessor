/*
package com.ey.in;

import com.ey.in.model.Transaction;
import com.ey.in.service.TxnPreProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TxnPreprocessorServiceTest {


    @Mock
    private TxnPreProcessorService txnPreProcessorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPreProcessTransaction() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setId("txn123");
        List<Transaction> transactions = List.of(transaction);

        // Act
        txnPreProcessorService.preProcessTransaction(transactions);

        // Assert
        verify(txnPreProcessorService, times(1)).preProcessTransaction(transactions);
    }
}
*/
