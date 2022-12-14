package com.bank_accounts.controller;

import com.bank_accounts.exceptions.AccountDoesNotExistException;
import com.bank_accounts.model.Account;
import com.bank_accounts.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountServiceImpl accountService;

    @Autowired
    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/account/{iban}")
    public ResponseEntity<Optional<Account>> getAccountInfo(@PathVariable("iban") String iban) {
        Optional<Account> readAccount = accountService.readAccountInfo(iban);
        if (readAccount.isPresent()) {
            return new ResponseEntity<>(readAccount, HttpStatus.OK);
        } else {
            throw new AccountDoesNotExistException();
        }
    }

    @GetMapping("/account")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return new ResponseEntity<>(accountService.readAllAccounts(), HttpStatus.OK);
    }

    @PostMapping("/account/{ssn}")
    public ResponseEntity<Account> addAccount(@RequestBody Account account, @PathVariable("ssn") String ssn ) {
        accountService.createAccount(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PutMapping("/account/withdraw/{iban}")
    public ResponseEntity<Account> withdrawFunds(@PathVariable("iban") String iban, @RequestParam("amount") Double amount) {
        accountService.changeAccountBalance(iban, -amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/account/deposit/{iban}")
    public ResponseEntity<Account> depositFunds(@PathVariable("iban") String iban, @RequestParam("amount") Double amount) {
        accountService.changeAccountBalance(iban, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/account/{iban}")
    public ResponseEntity<Account> deleteAccount(@PathVariable("iban") String iban) {
        accountService.deleteAccount(iban);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
