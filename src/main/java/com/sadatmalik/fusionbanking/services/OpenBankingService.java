package com.sadatmalik.fusionbanking.services;


import com.sadatmalik.fusionbanking.model.Account;
import com.sadatmalik.fusionbanking.model.Transaction;

import java.util.List;

/**
 * Common methods that all open banking service classes must implement.
 *
 * @author sadatmalik
 */
public interface OpenBankingService {

    String getAuthorizationUrl();

    String getAccessToken(String authCode);

    Account[] getUserAccounts();

    List<Transaction> getTransactions(Account account);

}
