package com.sadatmalik.fusionbanking.services;

import com.sadatmalik.fusionbanking.FusionBankingApplication;
import com.sadatmalik.fusionbanking.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static com.sadatmalik.fusionbanking.controllers.TestUtils.mockAccount;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = FusionBankingApplication.class)
class DummyBarclaysSavingsTransactionServiceTest {

    @Autowired
    @Qualifier("dummyBarclaysSavingsTransactionService")
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testServiceContextLoads() {
        assertThat(transactionService).isNotNull();
    }

    @Test
    void testGetTransactions() {
        assertThat(
                transactionService
                        .getTransactions(mockAccount()))
                .hasOnlyElementsOfType(Transaction.class);

        assertThat(
                transactionService
                        .getTransactions(mockAccount()))
                .hasSize(14);
    }
}