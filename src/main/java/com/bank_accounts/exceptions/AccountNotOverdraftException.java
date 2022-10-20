package com.bank_accounts.exceptions;

public class AccountNotOverdraftException extends RuntimeException {
    public AccountNotOverdraftException() {
    }

    public AccountNotOverdraftException(String message) {
        super(message);
    }
}
