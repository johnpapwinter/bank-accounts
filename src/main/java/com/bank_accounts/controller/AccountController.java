package com.bank_accounts.controller;

import com.bank_accounts.model.Account;
import com.bank_accounts.model.Holder;
import com.bank_accounts.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/account/{iban}")
    public ResponseEntity<Optional<Account>> getAccountInfo(@PathVariable("iban") String iban) {
        Optional<Account> readAccount = accountService.readAccountInfo(iban);
        if (readAccount.isPresent()) {
            return new ResponseEntity<>(readAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/account")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return new ResponseEntity<>(accountService.readAllAccounts(), HttpStatus.OK);
    }

    @PostMapping("/account{ssn}")
    public ResponseEntity<Account> addAccount(@RequestBody Account account, @PathVariable("ssn") String ssn ) {
        accountService.createAccount(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PutMapping("/account/{iban}/withdraw")
    public ResponseEntity<Account> withdrawFunds(@PathVariable("iban") String iban, @RequestParam("amount") Double amount) {
        accountService.changeAccountBalance(iban, -amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/account/{iban}/withdraw")
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
