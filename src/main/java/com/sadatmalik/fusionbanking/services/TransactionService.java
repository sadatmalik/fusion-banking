package com.sadatmalik.fusionbanking.services;


import com.sadatmalik.fusionbanking.model.Account;
import com.sadatmalik.fusionbanking.model.Transaction;

import java.util.List;

/**
 * Common methods that all transaction service classes must implement.
 *
 * @author sadatmalik
 */
public interface TransactionService {
    List<Transaction> getTransactions(Account account);
}
