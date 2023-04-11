package com.bank_accounts.domain.exceptions;

public class HolderAlreadyExistsException extends RuntimeException {
    public HolderAlreadyExistsException() {
    }

    public HolderAlreadyExistsException(String message) {
        super(message);
    }
}
