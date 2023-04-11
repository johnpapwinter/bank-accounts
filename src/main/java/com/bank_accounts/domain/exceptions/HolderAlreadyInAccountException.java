package com.bank_accounts.domain.exceptions;

public class HolderAlreadyInAccountException extends RuntimeException {

    public HolderAlreadyInAccountException() {
        super();
    }

    public HolderAlreadyInAccountException(String message) {
        super(message);
    }
}
