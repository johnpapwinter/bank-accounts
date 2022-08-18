package com.bank_accounts.service.exceptions;

public class AccountHasBalanceException extends Exception {

    private static final long serialVersionUID = 1L;

    public AccountHasBalanceException(String iban, double balance) {
        super("Account with iban " + iban + " has a balance of " + balance + ". Cannot delete account with a positive balance.");
    }
}
