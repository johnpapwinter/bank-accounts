package com.bank_accounts.domain.exceptions;

public class SingleHolderAccountException extends RuntimeException {

    public SingleHolderAccountException() {
        super();
    }

    public SingleHolderAccountException(String message) {
        super(message);
    }
}
