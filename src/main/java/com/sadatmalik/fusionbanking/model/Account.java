package com.sadatmalik.fusionbanking.model;

import lombok.*;

/**
 * Pojo models a single user bank account.
 *
 * @author sadatmalik
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Account {
    private String accountId;
    private String name; // e.g. of account holder
    private AccountType type; // e.g current, savings, cash
    private double balance;
    private String currency;
    private String description;
    private Bank bank;
}