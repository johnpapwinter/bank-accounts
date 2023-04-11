package com.bank_accounts.domain.exceptions;

public class HolderNotInAccountException extends RuntimeException {

    public HolderNotInAccountException() {
        super();
    }

    public HolderNotInAccountException(String message) {
        super(message);
    }
}
