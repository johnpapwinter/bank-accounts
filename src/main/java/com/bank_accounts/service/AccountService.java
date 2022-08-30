package com.bank_accounts.service;

import com.bank_accounts.dao.AccountRepository;
import com.bank_accounts.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private final AccountRepository accountRepository;


    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public boolean createAccount(Account newAccount) {
        if (readAccountInfo(newAccount.getIban()).isPresent()) {
            throw new IllegalStateException();
        }
        accountRepository.save(newAccount);
        return true;
    }

    @Override
    public Optional<Account> readAccountInfo(String iban) {
        return accountRepository.findByIban(iban);

    }

    @Override
    public List<Account> readAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        if(accounts.isEmpty()) {
            throw new IllegalStateException();
        }
        return accounts;
    }

    @Override
    public boolean changeAccountBalance(String iban, Double amount) {
        Optional<Account> account = readAccountInfo(iban);
        if (account.isEmpty()) {
            throw new IllegalStateException();
        }
        double currentBalance = account.get().getBalance();
        double newBalance = currentBalance + amount;
        if (newBalance < 0) {
            if(checkIfOverdraft(account.get())) {
                account.get().setBalance(newBalance);
                accountRepository.save(account.get());
                return true;
            } else {
                throw new IllegalStateException();
            }
        }
        account.get().setBalance(newBalance);
        accountRepository.save(account.get());

        return true;
    }

    @Override
    public boolean deleteAccount(String iban) {
        Optional<Account> account = accountRepository.findByIban(iban);
        if (account.isEmpty()) {
            throw new IllegalStateException();
        }
        if (account.get().getBalance() > 0) {
            throw new IllegalStateException();
         }
        accountRepository.deleteById(account.get().getId());
        return true;
    }

    private boolean checkIfOverdraft(Account account) {
        return account.getOverdraft();
    }
}
