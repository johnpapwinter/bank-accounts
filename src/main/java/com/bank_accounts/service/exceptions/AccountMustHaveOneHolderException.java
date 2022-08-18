package com.bank_accounts.service.exceptions;

public class AccountMustHaveOneHolderException extends Exception {

    private static final long serialVersionUID = 1L;

    public AccountMustHaveOneHolderException(String iban) {
        super("Account with iban " + iban + " must have at least one holder.");
    }
}
