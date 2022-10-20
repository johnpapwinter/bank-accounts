package com.bank_accounts.exceptions;

public class AccountDoesNotExistException extends RuntimeException {
    public AccountDoesNotExistException() {
    }

    public AccountDoesNotExistException(String message) {
        super(message);
    }
}
