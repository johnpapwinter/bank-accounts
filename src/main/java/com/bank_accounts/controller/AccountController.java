package com.bank_accounts.controller;

import com.bank_accounts.model.Account;
import com.bank_accounts.model.Holder;
import com.bank_accounts.service.AccountService;
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
    public Optional<Account> getAccountInfo(@PathVariable("iban") String iban) {
        Optional<Account> readAccount = accountService.readAccountInfo(iban);
        if (readAccount.isPresent()) {
            return readAccount;
        } else {
            return null;
        }
    }

    @GetMapping("/account")
    public List<Account> getAllAccounts() {
        return accountService.readAllAccounts();
    }

    // TODO FIX THIS
    @PostMapping("/account{ssn}")
    public void addAccount(@RequestBody Account account, @PathVariable("ssn") String ssn ) {
        accountService.createAccount(account);
    }

    @PutMapping("/account/{iban}/withdraw/{amount}")
    public void withdrawFunds(@PathVariable("iban") String iban, @PathVariable("amount") Double amount) {
        accountService.changeAccountBalance(iban, -amount);
    }

    @PutMapping("/account/{iban}/deposit/{amount}")
    public void depositFunds(@PathVariable("iban") String iban, @PathVariable("amount") Double amount) {
        accountService.changeAccountBalance(iban, amount);
    }

    // TODO cleanup
    @PutMapping("/account/{iban}/add-holder/{ssn}")
    public void addHolderToAccount(@PathVariable("iban") String iban, @PathVariable("ssn") String ssn) {
//        accountService.addAccountHolder(iban, ssn);
    }

    // TODO FIX THIS
    @PutMapping("/account/{iban}/remove-holder")
    public void removeHolderFromAccount(@PathVariable("iban") String iban, @RequestBody Holder holder) {
//        accountService.removeAccountHolder(iban, holder);
    }


    @DeleteMapping("/account/{iban}")
    public void deleteAccount(@PathVariable("iban") String iban) {
        accountService.deleteAccount(iban);
    }

}
