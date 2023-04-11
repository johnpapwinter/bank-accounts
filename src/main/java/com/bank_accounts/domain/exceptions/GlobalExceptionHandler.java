package com.bank_accounts.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AccountAlreadyExistsException.class)
    public ResponseEntity<Object> handlesAccountAlreadyExistsException(AccountAlreadyExistsException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccountBalanceNotZeroException.class)
    public ResponseEntity<Object> handlesAccountBalanceNotZeroException(AccountBalanceNotZeroException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccountDoesNotExistException.class)
    public ResponseEntity<Object> handlesAccountDoesNotExistException(AccountDoesNotExistException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AccountNotOverdraftException.class)
    public ResponseEntity<Object> handlesAccountNotOverdraftException(AccountNotOverdraftException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HolderAlreadyExistsException.class)
    public ResponseEntity<Object> handlesHolderAlreadyExistsException(HolderAlreadyExistsException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HolderDoesNotExistException.class)
    public ResponseEntity<Object> handlesHolderDoesNotExistException(HolderDoesNotExistException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoAccountsExistException.class)
    public ResponseEntity<Object> handlesNoAccountsExistException(NoAccountsExistException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoHoldersExistException.class)
    public ResponseEntity<Object> handlesNoHoldersExistException(NoHoldersExistException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InsufficientFundsException.class)
    public ResponseEntity<Object> handlesInsufficientFundsException(InsufficientFundsException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HolderAlreadyInAccountException.class)
    public ResponseEntity<Object> handlesHolderAlreadyInAccountException(HolderAlreadyInAccountException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HolderNotInAccountException.class)
    public ResponseEntity<Object> handlesHolderNotInAccountException(HolderNotInAccountException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SingleHolderAccountException.class)
    public ResponseEntity<Object> handlesSingleHolderAccountException(SingleHolderAccountException e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }


    /**
     * General Exceptions
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handlesGeneralException(Exception e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
}

