package com.bank_accounts.exceptions;

public class HolderDoesNotExistException extends RuntimeException {
    public HolderDoesNotExistException() {
    }

    public HolderDoesNotExistException(String message) {
        super(message);
    }
}
