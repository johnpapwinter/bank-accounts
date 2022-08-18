package com.bank_accounts.service.exceptions;

public class InsufficientFundsException extends Exception {
    private static final long serialVersionUID = 1L;

    public InsufficientFundsException(String iban) {
        super("The account " + iban + " has insufficient funds for the withdrawal amount you requested.");
    }
}
