package com.sadatmalik.fusionbanking.services;

import com.sadatmalik.fusionbanking.FusionBankingApplication;
import com.sadatmalik.fusionbanking.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.sadatmalik.fusionbanking.controllers.TestUtils.mockAccount;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = FusionBankingApplication.class)
class AccountServicesRegistryTest {

    @Autowired
    private AccountServicesRegistry accountServicesRegistry;

    private TransactionService transactionService;

    private Account account;

    @BeforeEach
    public void setup() {
        transactionService = new DummyTransactionService();
        account = mockAccount();

        accountServicesRegistry.registerTransactionService(
                account, transactionService
        );
    }

    @Test
    public void testServiceContextLoads() {
        assertThat(accountServicesRegistry).isNotNull();
    }

    @Test
    void getTransactionServiceFor() {
        assertThat(
                accountServicesRegistry
                        .getTransactionServiceFor(account))
                .isEqualTo(transactionService);
    }
}