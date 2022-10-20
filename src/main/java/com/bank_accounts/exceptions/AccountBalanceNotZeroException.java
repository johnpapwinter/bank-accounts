package com.bank_accounts.exceptions;

public class AccountBalanceNotZeroException extends RuntimeException {
    public AccountBalanceNotZeroException() {
    }

    public AccountBalanceNotZeroException(String message) {
        super(message);
    }
}
