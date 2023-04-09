package com.bank_accounts.domain.exceptions;

public class AccountNotOverdraftException extends RuntimeException {
    public AccountNotOverdraftException() {
    }

    public AccountNotOverdraftException(String message) {
        super(message);
    }
}
