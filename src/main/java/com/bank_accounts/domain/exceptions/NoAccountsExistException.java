package com.bank_accounts.domain.exceptions;

public class NoAccountsExistException extends RuntimeException{
    public NoAccountsExistException() {
    }

    public NoAccountsExistException(String message) {
        super(message);
    }
}
