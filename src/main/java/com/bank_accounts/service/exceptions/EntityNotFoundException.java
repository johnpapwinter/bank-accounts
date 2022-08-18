package com.bank_accounts.service.exceptions;

public class EntityNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String  ssn) {
        super("Holder with ssn " + ssn + " does not exist");
    }
}
