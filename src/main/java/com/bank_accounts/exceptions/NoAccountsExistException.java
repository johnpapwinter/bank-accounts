package com.bank_accounts.exceptions;

public class NoAccountsExistException extends RuntimeException{
    public NoAccountsExistException() {
    }

    public NoAccountsExistException(String message) {
        super(message);
    }
}
