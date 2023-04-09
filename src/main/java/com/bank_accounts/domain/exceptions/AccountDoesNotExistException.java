package com.bank_accounts.domain.exceptions;

public class AccountDoesNotExistException extends RuntimeException {
    public AccountDoesNotExistException() {
    }

    public AccountDoesNotExistException(String message) {
        super(message);
    }
}
