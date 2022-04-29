package com.sadatmalik.fusionbanking.controllers;

import com.sadatmalik.fusionbanking.model.Account;
import com.sadatmalik.fusionbanking.model.AccountType;
import com.sadatmalik.fusionbanking.model.Bank;

/**
 * @author sm@creativefusion.net
 */
public class TestUtils {

    public static Account mockAccount() {
        Bank bank = Bank.builder()
                .name("HSBC")
                .imageLocation("/images/hsbc.png")
                .build();

        return Account.builder()
                .accountId("HS000345678")
                .type(AccountType.SAVINGS)
                .name("Mr Sadat Malik")
                .balance(1234.56)
                .currency("GBP")
                //.user(new User())
                .description("HSBC Savings account")
                .bank(bank)
                .build();

    }
}
