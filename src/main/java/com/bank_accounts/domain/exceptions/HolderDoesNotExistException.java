package com.bank_accounts.domain.exceptions;

public class HolderDoesNotExistException extends RuntimeException {
    public HolderDoesNotExistException() {
    }

    public HolderDoesNotExistException(String message) {
        super(message);
    }
}
