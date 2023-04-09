package com.bank_accounts.domain.exceptions;

public class NoHoldersExistException extends RuntimeException {
    public NoHoldersExistException() {
    }

    public NoHoldersExistException(String message) {
        super(message);
    }
}
