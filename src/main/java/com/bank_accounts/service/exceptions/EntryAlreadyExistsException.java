package com.bank_accounts.service.exceptions;

public class EntryAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntryAlreadyExistsException(String ssn) {
        super("Holder with ssn " + ssn + " already exists");
    }
}
