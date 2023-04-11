package com.bank_accounts.controller;

import com.bank_accounts.domain.dto.AccountDTO;
import com.bank_accounts.domain.dto.HolderDTO;
import com.bank_accounts.domain.entities.Account;
import com.bank_accounts.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountServiceImpl accountService;

    @Autowired
    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/info/{iban}")
    public ResponseEntity<AccountDTO> getAccountInfo(@PathVariable String iban) {
        AccountDTO response = accountService.getAccountByIban(iban);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AccountDTO>> getAllAccounts(Pageable pageable) {
        Page<AccountDTO> response = accountService.getAllAccounts(pageable);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountDTO>> fetchAllAccounts() {
        List<AccountDTO> response = accountService.fetchAllAccounts();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/assign/{id}")
    public ResponseEntity<Void> addHolderToAccount(@RequestBody HolderDTO holderDTO, @PathVariable Long id) {
        accountService.assignHolderToAccount(id, holderDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<Void> removeHolderFromAccount(@RequestBody HolderDTO holderDTO, @PathVariable Long id) {
        accountService.removeHolderFromAccount(id, holderDTO);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/withdraw/{id}")
    public ResponseEntity<Void> withdrawFunds(@PathVariable Long id, @RequestParam("amount") Double amount) {
        accountService.withdrawFunds(id, amount);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/deposit/{id}")
    public ResponseEntity<Void> depositFunds(@PathVariable Long id, @RequestParam("amount") Double amount) {
        accountService.depositFunds(id, amount);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/overdraft/{id}")
    public ResponseEntity<Void> toggleOverdraft(@PathVariable Long id) {
        accountService.toggleOverDraft(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);

        return ResponseEntity.ok().build();
    }

}
