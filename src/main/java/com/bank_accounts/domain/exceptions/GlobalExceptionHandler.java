package com.bank_accounts.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AccountAlreadyExistsException.class)
    public ResponseEntity<Object> handlesAccountAlreadyExistsException(AccountAlreadyExistsException e) {
        return new ResponseEntity<>("Account already exists!", HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(value = AccountBalanceNotZeroException.class)
    public ResponseEntity<Object> handlesAccountBalanceNotZeroException(AccountBalanceNotZeroException e) {
        return new ResponseEntity<>("Account balance is not zero", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccountDoesNotExistException.class)
    public ResponseEntity<Object> handlesAccountDoesNotExistException(AccountDoesNotExistException e) {
        return new ResponseEntity<>("Account does not exist!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AccountNotOverdraftException.class)
    public ResponseEntity<Object> handlesAccountNotOverdraftException(AccountNotOverdraftException e) {
        return new ResponseEntity<>("Account not overdraft", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HolderAlreadyExistsException.class)
    public ResponseEntity<Object> handlesHolderAlreadyExistsException(HolderAlreadyExistsException e) {
        return new ResponseEntity<>("Holder already exists", HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(value = HolderDoesNotExistException.class)
    public ResponseEntity<Object> handlesHolderDoesNotExistException(HolderDoesNotExistException e) {
        return new ResponseEntity<>("Holder does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoAccountsExistException.class)
    public ResponseEntity<Object> handlesNoAccountsExistException(NoAccountsExistException e) {
        return new ResponseEntity<>("There are no accounts in the system", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoHoldersExistException.class)
    public ResponseEntity<Object> handlesNoHoldersExistException(NoHoldersExistException e) {
        return new ResponseEntity<>("There are not holders in the system", HttpStatus.NOT_FOUND);
    }
}
