package com.bank_accounts.service.exceptions;

public class NoAccountsFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public NoAccountsFoundException() {
        super("There are currently no active accounts!");
    }
}
